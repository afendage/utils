package com.remark;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * JDK 8 新特性
 */
public class JDK8_features {

    /**
     * Lambda表达式
     */
    @Test
    public void TestLambda() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        list.forEach(System.out::println);
        list.forEach(e -> System.out.println("方式二：" + e));
    }

    /**
     * Stream函数式操作流元素集合
     */
    @Test
    public void testStream() {
        List<Integer> nums = Lists.newArrayList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        System.out.println("求和：" + nums.stream()
                .filter(team -> team != null)
                .distinct()
                .mapToInt(num -> num)
                .skip(2)
                .limit(4)
                .peek(System.out::println)
                .sum()
        );
    }

    /**
     * 为了使接口具有更大的灵活性，Java8引入了一种新特性，接口声明方法可以有具体实现， 分为 default , static 两个类型声明
     * 当然子类可以重写，也可以不重写。 但是如果实现了多接口,默认方法名相同，必须复写默认方法
     */
    @Test
    public void testInterfaceChange() {
        JDK8_InterfaceChange.staticMethod();
        new JDK8_InterfaceChangeImpl().normalMethod();
        new JDK8_InterfaceChangeImpl_1().defaultMethod();
    }

    /**
     * 方法引用,与Lambda表达式联合使用
     */
    @Test
    public void testLambdaReference() {
        final Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car);
        cars.forEach(Car::collide);
        cars.forEach(Car::repair);
        final Car police = Car.create(Car::new);
        cars.forEach(police::follow);
    }

    @Test
    public void testDateTime() {
        //1.Clock
        final Clock clock = Clock.systemUTC();
        System.out.println(clock.instant());
        System.out.println(clock.millis());

        //2. ISO-8601格式且无时区信息的日期部分
        final LocalDate date = LocalDate.now();
        final LocalDate dateFromClock = LocalDate.now(clock);

        System.out.println(date);
        System.out.println(dateFromClock);

        // ISO-8601格式且无时区信息的时间部分
        final LocalTime time = LocalTime.now();
        final LocalTime timeFromClock = LocalTime.now(clock);

        System.out.println(time);
        System.out.println(timeFromClock);

        // 3.ISO-8601格式无时区信息的日期与时间
        final LocalDateTime datetime = LocalDateTime.now();
        final LocalDateTime datetimeFromClock = LocalDateTime.now(clock);

        System.out.println(datetime);
        System.out.println(datetimeFromClock);

        // 4.特定时区的日期/时间，
        final ZonedDateTime zonedDatetime = ZonedDateTime.now();
        final ZonedDateTime zonedDatetimeFromClock = ZonedDateTime.now(clock);
        final ZonedDateTime zonedDatetimeFromZone = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));

        System.out.println(zonedDatetime);
        System.out.println(zonedDatetimeFromClock);
        System.out.println(zonedDatetimeFromZone);

        //5.在秒与纳秒级别上的一段时间
        final LocalDateTime from = LocalDateTime.of(2014, Month.APRIL, 16, 0, 0, 0);
        final LocalDateTime to = LocalDateTime.of(2015, Month.APRIL, 16, 23, 59, 59);

        final Duration duration = Duration.between(from, to);
        System.out.println("Duration in days: " + duration.toDays());
        System.out.println("Duration in hours: " + duration.toHours());

    }

    /**
     * 8.新增base64加解密API
     */
    @Test
    public void testBase64() {
        final String text = "就是要测试加解密！！abjdkhdkuasu!!@@@@";
        String encoded = Base64.getEncoder()
                .encodeToString(text.getBytes(StandardCharsets.UTF_8));
        System.out.println("加密后=" + encoded);

        final String decoded = new String(
                Base64.getDecoder().decode(encoded),
                StandardCharsets.UTF_8);
        System.out.println("解密后=" + decoded);
    }

    /**
     * 9.数组并行（parallel）操作
     */
    @Test
    public void testParallel() {
        long[] arrayOfLong = new long[20000];
        //1.给数组随机赋值
        Arrays.parallelSetAll(arrayOfLong,
                index -> ThreadLocalRandom.current().nextInt(1000000));
        //2.打印出前10个元素
        Arrays.stream(arrayOfLong).limit(10).forEach(
                i -> System.out.print(i + " "));
        System.out.println();

        //3.数组排序
        Arrays.parallelSort(arrayOfLong);
        //4.打印排序后的前10个元素
        Arrays.stream(arrayOfLong).limit(10).forEach(
                i -> System.out.print(i + " "));
        System.out.println();
    }
    /**
     * 10.JVM的PermGen空间被移除：取代它的是Metaspace（JEP 122）元空间
     */
    @Test
    public void testMetaspace(){
        //-XX:MetaspaceSize初始空间大小，达到该值就会触发垃圾收集进行类型卸载，同时GC会对该值进行调整
        //-XX:MaxMetaspaceSize最大空间，默认是没有限制
        //-XX:MinMetaspaceFreeRatio在GC之后，最小的Metaspace剩余空间容量的百分比，减少为分配空间所导致的垃圾收集
        //-XX:MaxMetaspaceFreeRatio在GC之后，最大的Metaspace剩余空间容量的百分比，减少为释放空间所导致的垃圾收集
    }

    public static class Car {

        public static Car create(final Supplier<Car> supplier) {
            return supplier.get();
        }

        public static void collide(final Car car) {
            System.out.println("静态方法引用:" + car.toString());
        }

        public void repair() {
            System.out.println("任意对象的方法引用:" + this.toString());
        }

        public void follow(final Car car) {
            System.out.println("特定对象的方法引用:" + car.toString());
        }
    }


    public class JDK8_InterfaceChangeImpl implements JDK8_InterfaceChange {

        @Override
        public void normalMethod() {    // 非 default ,static 不便，都必须实现
            System.out.println("实现接口正常的方法");
        }
    }

    public class JDK8_InterfaceChangeImpl_1 implements JDK8_InterfaceChange, JDK8_InterfaceChange_1 {

        @Override
        public void normalMethod() {    // 非 default ,static 不便，都必须实现
            System.out.println("实现接口正常的方法");
        }

        @Override
        public void defaultMethod() {   // 两个接口默认方法名相同， 必须实现重新
            System.out.println("重写多接口实现存在相同的默认方法名");
        }
    }

    public interface JDK8_InterfaceChange_1 {

        public default void defaultMethod() {
            System.out.println("接口的默认方法");
        }

        public static void staticMethod() {
            System.out.println("接口的静态方法");
        }
    }

}
