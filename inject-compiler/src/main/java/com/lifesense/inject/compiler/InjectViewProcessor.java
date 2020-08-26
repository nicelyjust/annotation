package com.lifesense.inject.compiler;

import com.google.auto.service.AutoService;
import com.nicely.inject.InjectView;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

/*
@AutoService(Processor.class)
public class TestProcessor extends AbstractProcessor {
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Test.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, SWH!")
                .build();
        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();
        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}*/
@AutoService(Processor.class)
public class InjectViewProcessor extends AbstractProcessor{
    // 用来分类注解的,每个类的注解分开
    private Map<String, List<VariableElement>> mMap;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMap = new HashMap<>();
    }

    // apt能处理哪一些注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(InjectView.class.getCanonicalName());
    }
    // 能支持到Java的最低版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 找出所有InjectView注解,并分类
        Set<? extends VariableElement> elementsAnnotatedWith = (Set<? extends VariableElement>) roundEnv.getElementsAnnotatedWith(InjectView.class);
        for (VariableElement element : elementsAnnotatedWith) {
            String targetName = element.getEnclosingElement().getSimpleName().toString();
            List<VariableElement> variableElements = mMap.get(targetName);
            if (variableElements == null) {
                variableElements = new ArrayList<>();
                mMap.put(targetName, variableElements);
            }
            variableElements.add(element);
        }
        // TypeElement 类元素
        // ExecutableElement 可执行元素
        // VariableElement 属性元素

        // 对应的生成类文件
        if (mMap.size() > 0) {
            Writer writer = null;
            Iterator<String> iterator = mMap.keySet().iterator();
            while (iterator.hasNext()) {
                String targetName = iterator.next();
                List<VariableElement> elementList = mMap.get(targetName);

                TypeElement enclosingElement = (TypeElement) elementList.get(0).getEnclosingElement();
                String packageName = processingEnv.getElementUtils().getPackageOf(enclosingElement).toString();

                //写入文件

                try{
                    JavaFileObject sourceFile=processingEnv.getFiler().createSourceFile(packageName+"."+targetName+"_ViewBinding");
                    writer=sourceFile.openWriter();
                    //导包
                    writer.write("package "+packageName+";\n");
                    writer.write("import android.view.View;\n");
                    // 类
                    writer.write("public class "+targetName+"_ViewBinding {\n");
                    //接口方法
                    writer.write("public "+targetName+"_ViewBinding("+packageName+"."+targetName+" target, android.view.View source){\n");
                    // this.target = ()
                    for (VariableElement variableElement : elementList) {
                        //得到名字
                        String variableName=variableElement.getSimpleName().toString();
                        //得到ID
                        int id=variableElement.getAnnotation(InjectView.class).value();
                        //得到类型
                        TypeMirror typeMirror=variableElement.asType();
                        //target.textView = (android.widget.TextView) target.findViewById(2131165359);
                        writer.write("target."+variableName+"=("+typeMirror+")source.findViewById("+id+");\n");
                    }
                    writer.write("\n}\n}" );

                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    if(writer!=null){
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

        }
        return false;
    }
}