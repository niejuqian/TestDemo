package com.testdemo.retrofit.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by linving on 2016/11/23.
 * <p>
 * 用户账户信息
 */
public class AccountData implements Serializable {
    private BigDecimal amount;//可消费余额（包括消费卡）
    private BigDecimal cashAmount;//可提现余额
    private BigDecimal couponAmount;//消费卡余额（只能用于三享精品）
    private BigDecimal highGradeAmount;//优质卡余额（可用于优质商家跟三享精品）
    private BigDecimal universalAmount;//余额（可用于联盟商家、优质商家、三享精品）
    private BigDecimal consumePointsAmount;//消费积分余额
    private BigDecimal withdrawTotal;//累计提现总额
    private BigDecimal consumeTotal;//累计平台消费总额
    private BigDecimal freezeAmount;//冻结的资金
    private BigDecimal rebateTotal;//累计消费返利总额
    private BigDecimal performanceTotal;//累计绩效收益总额
    private BigDecimal referTotal;//累计推荐收益总额
    private BigDecimal agencyTotal;//累计代理收益总额

    public BigDecimal getAmount() {
        return amount == null ? new BigDecimal(0) : amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCashAmount() {
        return cashAmount == null ? new BigDecimal(0) : cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount == null ? new BigDecimal(0) : couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getConsumePointsAmount() {
        return consumePointsAmount == null ? new BigDecimal(0) : consumePointsAmount;
    }

    public void setConsumePointsAmount(BigDecimal consumePointsAmount) {
        this.consumePointsAmount = consumePointsAmount;
    }

    public BigDecimal getWithdrawTotal() {
        return withdrawTotal;
    }

    public void setWithdrawTotal(BigDecimal withdrawTotal) {
        this.withdrawTotal = withdrawTotal;
    }

    public BigDecimal getConsumeTotal() {
        return consumeTotal;
    }

    public void setConsumeTotal(BigDecimal consumeTotal) {
        this.consumeTotal = consumeTotal;
    }

    public BigDecimal getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(BigDecimal freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public BigDecimal getRebateTotal() {
        return rebateTotal;
    }

    public void setRebateTotal(BigDecimal rebateTotal) {
        this.rebateTotal = rebateTotal;
    }

    public BigDecimal getPerformanceTotal() {
        return performanceTotal;
    }

    public void setPerformanceTotal(BigDecimal performanceTotal) {
        this.performanceTotal = performanceTotal;
    }

    public BigDecimal getReferTotal() {
        return referTotal;
    }

    public void setReferTotal(BigDecimal referTotal) {
        this.referTotal = referTotal;
    }

    public BigDecimal getAgencyTotal() {
        return agencyTotal;
    }

    public void setAgencyTotal(BigDecimal agencyTotal) {
        this.agencyTotal = agencyTotal;
    }

    public BigDecimal getHighGradeAmount() {
        return highGradeAmount;
    }

    public void setHighGradeAmount(BigDecimal highGradeAmount) {
        this.highGradeAmount = highGradeAmount;
    }

    public BigDecimal getUniversalAmount() {
        return universalAmount;
    }

    public void setUniversalAmount(BigDecimal universalAmount) {
        this.universalAmount = universalAmount;
    }
}
