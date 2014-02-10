/*
 * Copyright (c) 2012 Whistler AB. All rights reserved.
 */

package se.redbridge.portlets.news;

import java.util.Date;

public class Blog implements Comparable<Blog>{
    private String text;
    private Date createdAt;
    private String targetPath;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    @Override
    public int compareTo(Blog o) {
        return o.createdAt.compareTo(this.createdAt);
    }
}
