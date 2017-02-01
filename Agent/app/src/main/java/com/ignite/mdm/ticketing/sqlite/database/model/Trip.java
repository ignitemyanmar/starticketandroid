
package com.ignite.mdm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trip {

    @Expose
    private String id;
    @Expose
    private String operator;
    @SerializedName("operator_id")
    @Expose
    private String operatorId;
    @SerializedName("from_name")
    @Expose
    private String fromName;
    @SerializedName("to_name")
    @Expose
    private String toName;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("available_day")
    @Expose
    private String availableDay;
    @Expose
    private String price;
    @SerializedName("foreign_price")
    @Expose
    private String foreignPrice;
    @Expose
    private String time;
    @SerializedName("departure_time")
    @Expose
    private String departureTime;
    @Expose
    private String tripdeparturetime;
    @SerializedName("min_availabletime")
    @Expose
    private String minAvailabletime;
    @SerializedName("time_checking")
    @Expose
    private String timeChecking;
    @SerializedName("total_seat")
    @Expose
    private int totalSeat;
    @SerializedName("permitseat_total")
    @Expose
    private int permitseatTotal;
    @SerializedName("permitseat_soldtotal")
    @Expose
    private int permitseatSoldtotal;
    @SerializedName("time_unit")
    @Expose
    private int timeUnit;
    @SerializedName("pre_order_time")
    @Expose
    private String preOrderTime;
    @Expose
    private String operator_name;
    @Expose
    public String operatorName;
    @Expose
    public String class_name;
    @Expose
    public String commission;
    @Expose
    public String everClose;
    @Expose
    public String fromCloseDate;
    @Expose
    public String toCloseDate;
    @Expose
    public String remark;
    @Expose
    public String seatPlanId;
    @Expose
    public String status;
    @Expose
    public String createdAt;
    @Expose
    public String updatedAt;
    
    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 
     * @param operator
     *     The operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 
     * @return
     *     The operatorId
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * 
     * @param operatorId
     *     The operator_id
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * 
     * @return
     *     The fromName
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * 
     * @param fromName
     *     The from_name
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * 
     * @return
     *     The toName
     */
    public String getToName() {
        return toName;
    }

    /**
     * 
     * @param toName
     *     The to_name
     */
    public void setToName(String toName) {
        this.toName = toName;
    }

    /**
     * 
     * @return
     *     The _class
     */
    public String getClass_() {
        return _class;
    }

    /**
     * 
     * @param _class
     *     The class
     */
    public void setClass_(String _class) {
        this._class = _class;
    }

    /**
     * 
     * @return
     *     The availableDay
     */
    public String getAvailableDay() {
        return availableDay;
    }

    /**
     * 
     * @param availableDay
     *     The available_day
     */
    public void setAvailableDay(String availableDay) {
        this.availableDay = availableDay;
    }

    /**
     * 
     * @return
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The foreignPrice
     */
    public String getForeignPrice() {
        return foreignPrice;
    }

    /**
     * 
     * @param foreignPrice
     *     The foreign_price
     */
    public void setForeignPrice(String foreignPrice) {
        this.foreignPrice = foreignPrice;
    }

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 
     * @return
     *     The departureTime
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * 
     * @param departureTime
     *     The departure_time
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * 
     * @return
     *     The tripdeparturetime
     */
    public String getTripdeparturetime() {
        return tripdeparturetime;
    }

    /**
     * 
     * @param tripdeparturetime
     *     The tripdeparturetime
     */
    public void setTripdeparturetime(String tripdeparturetime) {
        this.tripdeparturetime = tripdeparturetime;
    }

    /**
     * 
     * @return
     *     The minAvailabletime
     */
    public String getMinAvailabletime() {
        return minAvailabletime;
    }

    /**
     * 
     * @param minAvailabletime
     *     The min_availabletime
     */
    public void setMinAvailabletime(String minAvailabletime) {
        this.minAvailabletime = minAvailabletime;
    }

    /**
     * 
     * @return
     *     The timeChecking
     */
    public String getTimeChecking() {
        return timeChecking;
    }

    /**
     * 
     * @param timeChecking
     *     The time_checking
     */
    public void setTimeChecking(String timeChecking) {
        this.timeChecking = timeChecking;
    }

    /**
     * 
     * @return
     *     The totalSeat
     */
    public int getTotalSeat() {
        return totalSeat;
    }

    /**
     * 
     * @param totalSeat
     *     The total_seat
     */
    public void setTotalSeat(int totalSeat) {
        this.totalSeat = totalSeat;
    }

    /**
     * 
     * @return
     *     The permitseatTotal
     */
    public int getPermitseatTotal() {
        return permitseatTotal;
    }

    /**
     * 
     * @param permitseatTotal
     *     The permitseat_total
     */
    public void setPermitseatTotal(int permitseatTotal) {
        this.permitseatTotal = permitseatTotal;
    }

    /**
     * 
     * @return
     *     The permitseatSoldtotal
     */
    public int getPermitseatSoldtotal() {
        return permitseatSoldtotal;
    }

    /**
     * 
     * @param permitseatSoldtotal
     *     The permitseat_soldtotal
     */
    public void setPermitseatSoldtotal(int permitseatSoldtotal) {
        this.permitseatSoldtotal = permitseatSoldtotal;
    }

    /**
     * 
     * @return
     *     The timeUnit
     */
    public int getTimeUnit() {
        return timeUnit;
    }

    /**
     * 
     * @param timeUnit
     *     The time_unit
     */
    public void setTimeUnit(int timeUnit) {
        this.timeUnit = timeUnit;
    }
    
    

	public String getPreOrderTime() {
		return preOrderTime;
	}

	public void setPreOrderTime(String preOrderTime) {
		this.preOrderTime = preOrderTime;
	}
	
	

	public String getOperator_name() {
		return operator_name;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}

	
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getEverClose() {
		return everClose;
	}

	public void setEverClose(String everClose) {
		this.everClose = everClose;
	}

	public String getFromCloseDate() {
		return fromCloseDate;
	}

	public void setFromCloseDate(String fromCloseDate) {
		this.fromCloseDate = fromCloseDate;
	}

	public String getToCloseDate() {
		return toCloseDate;
	}

	public void setToCloseDate(String toCloseDate) {
		this.toCloseDate = toCloseDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSeatPlanId() {
		return seatPlanId;
	}

	public void setSeatPlanId(String seatPlanId) {
		this.seatPlanId = seatPlanId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Trip [id=" + id + ", operator=" + operator + ", operatorId="
				+ operatorId + ", fromName=" + fromName + ", toName=" + toName
				+ ", _class=" + _class + ", availableDay=" + availableDay
				+ ", price=" + price + ", foreignPrice=" + foreignPrice
				+ ", time=" + time + ", departureTime=" + departureTime
				+ ", tripdeparturetime=" + tripdeparturetime
				+ ", minAvailabletime=" + minAvailabletime + ", timeChecking="
				+ timeChecking + ", totalSeat=" + totalSeat
				+ ", permitseatTotal=" + permitseatTotal
				+ ", permitseatSoldtotal=" + permitseatSoldtotal
				+ ", timeUnit=" + timeUnit + ", preOrderTime=" + preOrderTime
				+ ", operator_name=" + operator_name + ", operatorName="
				+ operatorName + ", class_name=" + class_name + ", commission="
				+ commission + ", everClose=" + everClose + ", fromCloseDate="
				+ fromCloseDate + ", toCloseDate=" + toCloseDate + ", remark="
				+ remark + ", seatPlanId=" + seatPlanId + ", status=" + status
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

	
}
