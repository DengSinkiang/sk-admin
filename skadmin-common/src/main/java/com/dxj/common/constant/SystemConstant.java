package com.dxj.common.constant;

/**
 * 系统级静态变量
 * SystemConstant.java
 */
public class SystemConstant {

    /**
     * 文件类型
     *
     * @author: Sinkiang
     */
    public enum FileType {
        /**
         * 图片
         */
        IMAGE(1),
        /**
         * 文档
         */
        DOCUMENT(2),
        /**
         * 视频
         */
        VIDEO(3),
        /**
         * 种子
         */
        SEED(4),
        /**
         * 音乐
         */
        MUSIC(5),
        /**
         * 其他
         */
        OTHER(6);

        private int value;

        FileType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
