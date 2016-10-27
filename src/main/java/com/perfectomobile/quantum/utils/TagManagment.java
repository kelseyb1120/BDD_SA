package com.perfectomobile.quantum.utils;

import gherkin.formatter.model.Tag;

import java.util.List;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

public class TagManagment {

    public static boolean isTagSelected(List<Tag> tags) {
        //gettag
        boolean rc = false;

        String allowdTags = getBundle().getString("Perfecto.execute.tag");

        // if parameter is null > allow all
        if (allowdTags == null) {
            return true;
        }

        String[] allowdTagsArr = allowdTags.split(",");
        for (int i = 0; i < allowdTagsArr.length; i++) {
            for (Tag T : tags) {
                if (T.getName().equals(allowdTagsArr[i])) {
                    return true;
                }
            }
        }

        return rc;
    }
}
