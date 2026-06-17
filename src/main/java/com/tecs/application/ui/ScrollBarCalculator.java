package com.tecs.application.ui;

public final class ScrollBarCalculator {
    
    public ScrollBar calculate(int totalRows, int visibleRows, int rowOffset) {
        
        if(totalRows <= visibleRows) {
            return new ScrollBar(0, visibleRows);
        }

        int thumbHeight = Math.max(1, (visibleRows * visibleRows) / totalRows);
        int maxOffset = totalRows - visibleRows;
        
        int maxThumbTop = visibleRows - thumbHeight;

        int thumbTop = (rowOffset * maxThumbTop) / maxOffset;
        
        return new ScrollBar(thumbTop, thumbHeight);
    }
}
