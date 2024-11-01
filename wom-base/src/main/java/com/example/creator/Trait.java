package com.example.creator;

/**
 * 基于优先组合而非继承的理念 实现的一种抽象方法 命名参考自 Rust
 * <p>
 * 继承与组合对比
 * <ol>
 *     <li>继承必须编译时确定 组合允许运行时修改</li>
 *     <li>继承是白盒复用 直观但耦合度高 组合是黑盒复用 更符合低耦合高内聚</li>
 * </ol>
 */
public interface Trait {
    void doFunction();
}
