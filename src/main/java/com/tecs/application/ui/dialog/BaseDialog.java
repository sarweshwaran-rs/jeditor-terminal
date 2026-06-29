package com.tecs.application.ui.dialog;

public abstract class BaseDialog implements Dialog{
    
    protected final String title;
    protected boolean closed;
    protected DialogResult result;
    protected int width;
    protected int height;

    protected BaseDialog(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public DialogResult result() {
        return result;
    }

    protected void cancel() {
        result = new DialogResult(DialogAction.CANCEL, "");
        closed = true;
    }

    protected void close(DialogAction action, String value) {
        result = new DialogResult(action, value);
        closed = true;
    }
}
