package com.example.creator;

/**
 * 组合和委托实现复用
 * 或者名称后缀改为 Able ？暂没有好的想法。
 * <ul>
 *     <li>委托来自 Object-Oriented Design</li>
 *     <li>这里参考 Component-Oriented Design</li>
 *     <li>更极端的 Data-Oriented Design 则将数据与逻辑拆分 两类对象的相互作用可以统一实现 如 ECS 架构</li>
 * </ul>
 * P.S.
 * OOA(Analysis) -> OOD(Design) -> OOP(Programming) 相对的面向过程有结构化 (Structured)
 * <p>
 * P.S.
 * OOP 面向对象编程的层层抽象（大量虚函数表）不可避免的带来运行效率问题，在性能敏感的游戏编程领域中是不愿看到的。
 * 相对的 DOP 面向数据编程则可利用 CPU 缓存和分支预测等特性有较高性能，数据与逻辑的分离显得不怎么内聚。
 * <p>
 * P.S.
 * 另有 DDD(Domain-Driven Design) 领域驱动设计 门槛较高不在这里讲述
 */
public interface TraitWithin extends Trait {
    Trait takeTrait();

    @Override
    default void doFunction() {
        this.takeTrait().doFunction();
    }
}
