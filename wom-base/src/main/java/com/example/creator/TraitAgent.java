package com.example.creator;

/**
 * 组合和委托实现复用
 * <ul>
 *     <li>委托来自 Object-Oriented Design</li>
 *     <li>这里参考 Component-Oriented Design</li>
 *     <li>更极端的 Data-Oriented Design 则将数据与逻辑拆分 两类对象的相互作用可以统一实现 如 ECS 架构</li>
 * </ul>
 * P.S. OOA(Analysis) -> OOD(Design) -> OOP(Programming) 相对的面向过程有结构化 (Structured)
 * <p>
 * P.S. 另有 DDD(Domain-Driven Design) 领域驱动设计 门槛较高不在这里讲述
 */
public interface TraitAgent extends Trait {
    Trait asTrait();

    @Override
    default void doFunction() {
        this.asTrait().doFunction();
    }
}
