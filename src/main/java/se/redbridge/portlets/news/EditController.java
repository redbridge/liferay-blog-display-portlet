/*
 * Copyright (c) 2012 Whistler AB. All rights reserved.
 */

package se.redbridge.portlets.news;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;
import java.io.IOException;
import java.util.List;

@ManagedBean
@SessionScoped
public class EditController implements Serializable {
    private static final long serialVersionUID = 1L;

    private int count;

    @PostConstruct
    public void load() {
        final LiferayFacesContext ctx = LiferayFacesContext.getInstance();
        count = ctx.getPortletPreferenceAsInt(Constants.KEY_BLOG_COUNT, 5);
    }

    public String update() {
        final LiferayFacesContext ctx = LiferayFacesContext.getInstance();
        final PortletPreferences pref = ctx.getPortletPreferences();
        try {
            pref.setValue(Constants.KEY_BLOG_COUNT, String.valueOf(count));
            pref.store();
        } catch (ReadOnlyException e) {
            final FacesMessage msg = new FacesMessage("Values can not be updated.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (IOException e) {
            final FacesMessage msg = new FacesMessage("Values can not be stored.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (ValidatorException e) {
            final FacesMessage msg = new FacesMessage("Values did not pass validation.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return "edit";
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
