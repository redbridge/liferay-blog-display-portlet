/*
 * Copyright (c) 2012 Whistler AB. All rights reserved.
 */

package se.redbridge.portlets.news;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;

@ManagedBean
@SessionScoped
public class ViewController {
    public Collection<Blog> getNewsList() {
        final LiferayFacesContext ctx = LiferayFacesContext.getInstance();
        int count = ctx.getPortletPreferenceAsInt(Constants.KEY_BLOG_COUNT, 5);

        List<Blog> blogList = new ArrayList<>();

        try {
            List<BlogsEntry> blogs = BlogsEntryLocalServiceUtil.getBlogsEntries(0, Integer.MAX_VALUE);
            for (BlogsEntry b : blogs) {
                if (b.isInactive() == false && b.isExpired() == false && b.isDenied() == false && b.isDraft() == false) {
                    Blog blog = new Blog();
                    String contentByLocale = b.getDescription();

                    if (contentByLocale.length() >= 150) {
                        contentByLocale = contentByLocale.substring(0, 150);
                        contentByLocale += "...";
                    }

                    blog.setText(contentByLocale);
                    blog.setCreatedAt(b.getDisplayDate());
                    blog.setTargetPath("/web/" + b.getUserName() + "/blog/-/blogs/" + b.getUrlTitle());
                    blogList.add(blog);
                }
            }
        } catch (SystemException e) {
            throw new FacesException("Could not load news!", e);
        }

        Collections.sort(blogList);

        return blogList.subList(0, Math.min(blogList.size(), count - 1));
    }
}
