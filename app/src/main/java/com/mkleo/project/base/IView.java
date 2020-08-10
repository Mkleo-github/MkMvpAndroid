package com.mkleo.project.base;

import androidx.lifecycle.LifecycleOwner;

/**
 * View抽象
 */
public interface IView extends LifecycleOwner {
    /**
     * UI控制器
     *
     * @return
     */
    UiKit getUiKit();
}
