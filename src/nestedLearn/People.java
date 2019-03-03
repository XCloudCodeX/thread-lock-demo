package nestedLearn;

/**
 * Created with IntelliJ IDEA.
 * User: CloudCx
 * Date: 2019/3/3
 * Time: 21:21
 * Description: 内部类学习
 */
public class People {

    public static int count =0;
    private double aDouble = 1D;

    public People(double aDouble) {
        this.aDouble = aDouble;
    }
    public void outInerSay(String s){
        System.out.println("外部类同名方法"+s+"\t"+aDouble);
    }
    //people外部类需要访问内部类需要先创建获取内部类
    //创建一个方法来获取成员内部类
    public doSomething getInerClass(){
        return new doSomething();
    }
    //内部类
    class doSomething{
        //当成员内部类拥有和外部类同名的成员变量或者方法时，会发生隐藏现象，即默认情况下访问的是成员内部类的成员
        //想要访问外部成员需要使用  外部类.this.成员变量  的方式
        public double aDouble = 3D;
        public void say(){
            //内部类可以无条件访问外部类方法和变量
            //访问外部静态成员
            System.out.println(count);
            //访问外部私有遍历
            System.out.println(aDouble);
        }
        public void outInerSay(String s){
            System.out.println("内部类同名方法"+s+"\t"+aDouble);
        }
    }

    public static void main(String[] args) {
        //成员内部类是依附外部类而存在的，也就是说，如果要创建成员内部类的对象，前提是必须存在一个外部类的对象
        People p1 =new People(2D);
        p1.outInerSay("111111");
        //方法一初始化内部类
        People.doSomething pd = p1.new doSomething();
        //pd.say();
        pd.outInerSay("22222");
        People.doSomething pd2 = p1.getInerClass();
        pd2.outInerSay("3333");
    }
}
