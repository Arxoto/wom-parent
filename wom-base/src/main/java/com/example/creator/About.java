package com.example.creator;

public class About {
    /*
“抽象”
本质是少用 if ？

方法重写 Override
子类继承父类或实现接口，外在方法签名相同，但内在实现不同。
个人喜欢 C++ 中经常提的虚函数表这个名词，很生动形象， Java 不知道为什么提的不多。
本质是实现“抽象”的一种方式，可以类比为设计模式中的策略模式，由此引出思考：
    策略模式通过枚举（并非狭义地指枚举类），来进行选择，有点面向数据编程中数据与逻辑拆分的意思；
    类通过类名映射到虚函数表，来进行选择，有时会与工厂模式相结合来根据数据动态生成各种类；
    两种方式一定程度上能相互替代，那么该如何抉择？最佳实践？或者更极端，面向对象抑或是面向数据？
    点名 Rust 的枚举，因为可以有不同的属性，更容易引起这样的思考。亦或者说其设计本身就是出于此考虑的？
    参考 DDD 的思想：
        see https://developer.aliyun.com/article/792244
        and https://developer.aliyun.com/article/1436383
        一种实体为一个类，实体若再有细分则提取共性组合加上 Rust 的枚举实现个性，其他语言采取继承方式；
        实体有一系列规则约束，如：
            实体有唯一标识，值对象无标识；
            实体是充血模型，符合高内聚要求，内部保证自洽；
            聚合的概念？聚合到底是同一类实体的管理者（对象池模式），还是逻辑上被拥有的关系？待确定；
            实体的生命周期由聚合根管理：若被拥有者不依赖于拥有者，则拥有者仅保存其标识（与上一条相关）；
        涉及到不同实体之间的逻辑，会拆分到独立的类，称为领域服务 Domain Service ；
            领域服务不能直接修改实体（非贫血模型），污染实体（不被实体持有），可通过方法参数的形式注入（保证一致性）；
            领域服务使用接口抽象，同时尽量保证方法为纯函数，可用策略模式进行管理（抑或是领域服务内部使用策略模式？对领域服务的定义不够清晰）；
            跨实体业务逻辑，使用第三方领域服务，此时不要求纯函数，且影响多个实体，无法通过参数注入（实体内部不能影响其他实体）；
        在第三方领域服务中（非纯函数），当一个实体的状态改变会引起其他实体的状态改变（副作用，适用于同步异步均可）：
            在面向过程的写法为 do xx; if condition { do xx } ，这里是耦合且隐性的（条件应该写状态修改中的最后，然后通过事件触发另一个）；
            但是实体不能依赖通知总线（非持有），同时不能使用参数注入（修改了其他实体），因此通知方法必须为全局唯一（ Java 中的 static 方法）；
            todo 目前的局限

方法重载 Overload
Java 中重载意味着方法名字相同，但方法签名不同。
关于方法重载是否为多态是有一定争论的：
    运行时，方法签名不同，这个角度看不符合多态定义。像重写（ C++ 虚函数表），不同子类对象产生不同结果的才算多态。
    编译时，方法名词相同，这个角度看又符合多态定义。持此观点者提出动态多态（运行时确定）和静态多态（编译时确定）。
这里不对此进行表态，无论如何，方法重载是进行“抽象”的一种方式。
个人不喜欢重载这种行为，很容易将其与重写混淆：
    子类增强父类方法：
        class A { method(ParamX) }
        class B extends A { method(ParamY) }  // ParamY extends ParamX
    由于方法名称一致，编码时容易误解为是重写；
    但实际上方法签名不一致，实际为重载（也不知道为什么这俩翻译的这么像），运行时可能会调用父类方法；
    这种差异很容易导致 BUG ，也是排查问题的心智负担来源。私以为最好的解决方式是没有重载。
    与之类似的还有子类的同名属性带来的隐藏，私以为也是一个需要被舍弃的设计。

     */

}