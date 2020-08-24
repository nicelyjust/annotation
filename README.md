##什么是注解
能够添加到Java源代码的语法元数据。类、方法、变量、参数都能可以被注解
##有什么作用

- 标记，告诉编辑器一些信息
- 编译时动态处理，如动态生成代码
- 运行时动态处理，如得到注解信息

##有哪一些分类

- 标准的Annotation：Override（覆写）、Deprecated（弃用）、SuppressWarnings（忽略|压制警告），Java自带的几个Annotation；
- 元注解：@Retention, @Target, @Inherited, @Documented
- 自定义注解，定义时需要用到上面的元注解

##如何自定义注解
###元注解释义
1. @Retention:保留的范围,可理解为对Annotation的“生命周期”限制，SOURCE\CLASS\RUNTIME

	    SOURCE：Annotations are to be discarded by the compiler.编译时被丢弃
	    CLASS：这是一个默认的行为，存在编译时期，运行时被丢弃
	    RUNTIME：编译到运行，因此可以用反射读取

2. @Target:可以用来修饰哪些程序元素，如 TYPE, METHOD, CONSTRUCTOR, FIELD, PARAMETER等，未标注则表示可修饰所有
    
	    ElementType.TYPE,//类、接口（包括注解类型）以及枚举
	    ElementType.FIELD,//成员变量
	    ElementType.METHOD,//方法声明
	    ElementType.PARAMETER,//参数声明
	    ElementType.CONSTRUCTOR,//构造函数声明
	    ElementType.LOCAL_VARIABLE,//局部变量声明
	    ElementType.ANNOTATION_TYPE,//注解声明
	    ElementType.PACKAGE,//包声明
	    /**
	     * Type parameter declaration
	     * @since 1.8
	     */
	    ElementType.TYPE_PARAMETER,
	    /**
	     * Use of a type
	     * @since 1.8
	     */
	    ElementType.TYPE_USE
3. @Inherited：阐述了某个被标注的类型是被继承的。如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类。

### 注解参数
参数成员只能用基本数据类型byte,short,char,int,long,float,double,boolean八种基本数据类型和String,Enum,Class,annotations等数据类型，以及这一些类型的数组。
> 注解元素必须有确定的值,要么在定义注解的默认值中指定,要么在使用注解时指定。非基本数据类型的注解元素的值不可为null。

使用实例：

    @Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Cat {
    public enum Color{BLUE,GRAY,ORANGE,BLACK,WHITE}

    public int id() default -1;

    public Color color() default Color.BLACK;

    public String nickname() default "";

	}

###如何在Android工程中自定义注解
#### 注解定义三部曲:通用范式

1. 新建一个Java module(假设命名为inject),自定义的注解都放在此工程;
		
2. 新建一个Java module(假设命名为inject-compiler),注解器工程,负责处理你的自定义注解;
    
	    // 注册一个APT功能
	    annotationProcessor 'com.google.auto.service:auto-service:1.0-rc6'
	    compileOnly 'com.google.auto.service:auto-service:1.0-rc6'
	    implementation project(path: ':inject')
3. 在你的主工程build.gradle配置如下:
		
		//...
		annotationProcessor project(path: ':inject-compiler')
    	implementation project(path: ':inject')