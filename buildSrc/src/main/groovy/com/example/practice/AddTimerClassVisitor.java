package com.example.practice;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

//
// Created by skylar on 2022/5/14.
//
public class AddTimerClassVisitor extends ClassVisitor {

    public static String mOwner;

    public AddTimerClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        mOwner = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new TimeMethodVistor(api, methodVisitor, access, name, descriptor);
    }

    static class TimeMethodVistor extends AdviceAdapter {

        private boolean isInject = false;

        protected TimeMethodVistor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(api, methodVisitor, access, name, descriptor);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            System.out.println("visitAnnotation : " + descriptor);
            if("Lcom/skylar/annotation/MethodTime;".equals(descriptor)) {
                isInject = true;
            }
            return super.visitAnnotation(descriptor, visible);
        }

        @Override
        protected void onMethodEnter() {
            if(isInject) {
                visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                visitVarInsn(LSTORE, 0);
            }
            super.onMethodEnter();
        }

        @Override
        protected void onMethodExit(int opcode) {
            if(isInject) {
                visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                visitVarInsn(LSTORE, 2);
                visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                visitTypeInsn(NEW, "java/lang/StringBuilder");
                visitInsn(DUP);
                visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                visitLdcInsn(mOwner + " " +  getName() + " executed time : ");
                visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                visitVarInsn(LLOAD, 2);
                visitVarInsn(LLOAD, 0);
                visitInsn(LSUB);
                visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
                visitLdcInsn(" :ms");
                visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            }
            super.onMethodExit(opcode);
        }
    }
}
