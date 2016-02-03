package com.cu.http.container.core.filter;

import javax.servlet.Filter;

public interface BaseNettyFilter extends Filter{
    public boolean isPass();
}
