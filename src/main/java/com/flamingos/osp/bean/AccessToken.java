/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flamingos.osp.bean;

import java.sql.Timestamp;

/**
 *
 * @author developerPro
 */
public class AccessToken {
private Timestamp expireTime;
private char activeIndicator;
private int type;

	/**
     * @return the expireTime
     */
    public Timestamp getExpireTime() {
        return expireTime;
    }

    /**
     * @param expireTime the expireTime to set
     */
    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * @return the activeIndicator
     */
    public char getActiveIndicator() {
        return activeIndicator;
    }

    /**
     * @param activeIndicator the activeIndicator to set
     */
    public void setActiveIndicator(char activeIndicator) {
        this.activeIndicator = activeIndicator;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }
}
