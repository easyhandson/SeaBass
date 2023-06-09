package cn.com.seabase.framework.common.util.page;

import cn.com.seabase.framework.common.pojo.PageParam;

/**
 * 分页工具类
 */
public class PageUtil {

    /** 起始页的最小值 */
    private final static int FIRST_PAGE = 1;

    /**
     * 获取分页起始位置
     * */
    public static int getStart(int pageNo, int pageSize) {
        if (pageNo < FIRST_PAGE) {
            pageNo = FIRST_PAGE;
        }

        return (pageNo - FIRST_PAGE) * pageSize;
    }

    public static int getStart(PageParam pageParam) {
        return getStart(pageParam.getPageNo(), pageParam.getPageSize());
    }

}
