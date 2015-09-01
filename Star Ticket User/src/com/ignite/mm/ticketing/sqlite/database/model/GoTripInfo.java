package com.ignite.mm.ticketing.sqlite.database.model;

public class GoTripInfo {

	private String sale_order_no;
	private String price;
	private String seat_count;
	private String agentgroup_id;
	private String operator_id;
	private String Selected_seats;
	private String ticket_nos;
	private String busOccurence;
	private String permit_access_token;
	private String Permit_agent_id;
	private String permit_ip;
	private String BuyerName;
	private String BuyerPhone;
	private String BuyerNRC;
	private String FromCity;
	private String ToCity;
	private String Operator_Name;
	private String from_to;
	private String time;
	private String classes;
	private String date;
	private String ConfirmDate;
	private String ConfirmTime;
	private String ExtraCityID;
	private String ExtraCityName;
	private String ExtraCityPrice;
	private String ReturnDate;
	private String from_intent;
	private String TicketLists;
	private String permit_operator_id;
	
	
	public GoTripInfo() {
		super();
		// TODO Auto-generated constructor stub
	}


	public GoTripInfo(String sale_order_no, String price, String seat_count,
			String agentgroup_id, String operator_id, String selected_seats,
			String ticket_nos, String busOccurence, String permit_access_token,
			String permit_agent_id, String permit_ip, String buyerName,
			String buyerPhone, String buyerNRC, String fromCity, String toCity,
			String operator_Name, String from_to, String time, String classes,
			String date, String confirmDate, String confirmTime,
			String extraCityID, String extraCityName, String extraCityPrice,
			String returnDate, String from_intent, String ticketLists,
			String permit_operator_id) {
		super();
		this.sale_order_no = sale_order_no;
		this.price = price;
		this.seat_count = seat_count;
		this.agentgroup_id = agentgroup_id;
		this.operator_id = operator_id;
		Selected_seats = selected_seats;
		this.ticket_nos = ticket_nos;
		this.busOccurence = busOccurence;
		this.permit_access_token = permit_access_token;
		Permit_agent_id = permit_agent_id;
		this.permit_ip = permit_ip;
		BuyerName = buyerName;
		BuyerPhone = buyerPhone;
		BuyerNRC = buyerNRC;
		FromCity = fromCity;
		ToCity = toCity;
		Operator_Name = operator_Name;
		this.from_to = from_to;
		this.time = time;
		this.classes = classes;
		this.date = date;
		ConfirmDate = confirmDate;
		ConfirmTime = confirmTime;
		ExtraCityID = extraCityID;
		ExtraCityName = extraCityName;
		ExtraCityPrice = extraCityPrice;
		ReturnDate = returnDate;
		this.from_intent = from_intent;
		TicketLists = ticketLists;
		this.permit_operator_id = permit_operator_id;
	}


	public String getSale_order_no() {
		return sale_order_no;
	}


	public void setSale_order_no(String sale_order_no) {
		this.sale_order_no = sale_order_no;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getSeat_count() {
		return seat_count;
	}


	public void setSeat_count(String seat_count) {
		this.seat_count = seat_count;
	}


	public String getAgentgroup_id() {
		return agentgroup_id;
	}


	public void setAgentgroup_id(String agentgroup_id) {
		this.agentgroup_id = agentgroup_id;
	}


	public String getOperator_id() {
		return operator_id;
	}


	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}


	public String getSelected_seats() {
		return Selected_seats;
	}


	public void setSelected_seats(String selected_seats) {
		Selected_seats = selected_seats;
	}


	public String getTicket_nos() {
		return ticket_nos;
	}


	public void setTicket_nos(String ticket_nos) {
		this.ticket_nos = ticket_nos;
	}


	public String getBusOccurence() {
		return busOccurence;
	}


	public void setBusOccurence(String busOccurence) {
		this.busOccurence = busOccurence;
	}


	public String getPermit_access_token() {
		return permit_access_token;
	}


	public void setPermit_access_token(String permit_access_token) {
		this.permit_access_token = permit_access_token;
	}


	public String getPermit_agent_id() {
		return Permit_agent_id;
	}


	public void setPermit_agent_id(String permit_agent_id) {
		Permit_agent_id = permit_agent_id;
	}


	public String getPermit_ip() {
		return permit_ip;
	}


	public void setPermit_ip(String permit_ip) {
		this.permit_ip = permit_ip;
	}


	public String getBuyerName() {
		return BuyerName;
	}


	public void setBuyerName(String buyerName) {
		BuyerName = buyerName;
	}


	public String getBuyerPhone() {
		return BuyerPhone;
	}


	public void setBuyerPhone(String buyerPhone) {
		BuyerPhone = buyerPhone;
	}


	public String getBuyerNRC() {
		return BuyerNRC;
	}


	public void setBuyerNRC(String buyerNRC) {
		BuyerNRC = buyerNRC;
	}


	public String getFromCity() {
		return FromCity;
	}


	public void setFromCity(String fromCity) {
		FromCity = fromCity;
	}


	public String getToCity() {
		return ToCity;
	}


	public void setToCity(String toCity) {
		ToCity = toCity;
	}


	public String getOperator_Name() {
		return Operator_Name;
	}


	public void setOperator_Name(String operator_Name) {
		Operator_Name = operator_Name;
	}


	public String getFrom_to() {
		return from_to;
	}


	public void setFrom_to(String from_to) {
		this.from_to = from_to;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getClasses() {
		return classes;
	}


	public void setClasses(String classes) {
		this.classes = classes;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getConfirmDate() {
		return ConfirmDate;
	}


	public void setConfirmDate(String confirmDate) {
		ConfirmDate = confirmDate;
	}


	public String getConfirmTime() {
		return ConfirmTime;
	}


	public void setConfirmTime(String confirmTime) {
		ConfirmTime = confirmTime;
	}


	public String getExtraCityID() {
		return ExtraCityID;
	}


	public void setExtraCityID(String extraCityID) {
		ExtraCityID = extraCityID;
	}


	public String getExtraCityName() {
		return ExtraCityName;
	}


	public void setExtraCityName(String extraCityName) {
		ExtraCityName = extraCityName;
	}


	public String getExtraCityPrice() {
		return ExtraCityPrice;
	}


	public void setExtraCityPrice(String extraCityPrice) {
		ExtraCityPrice = extraCityPrice;
	}


	public String getReturnDate() {
		return ReturnDate;
	}


	public void setReturnDate(String returnDate) {
		ReturnDate = returnDate;
	}


	public String getFrom_intent() {
		return from_intent;
	}


	public void setFrom_intent(String from_intent) {
		this.from_intent = from_intent;
	}


	public String getTicketLists() {
		return TicketLists;
	}


	public void setTicketLists(String ticketLists) {
		TicketLists = ticketLists;
	}


	public String getPermit_operator_id() {
		return permit_operator_id;
	}


	public void setPermit_operator_id(String permit_operator_id) {
		this.permit_operator_id = permit_operator_id;
	}


	@Override
	public String toString() {
		return "GoTripInfo [sale_order_no=" + sale_order_no + ", price="
				+ price + ", seat_count=" + seat_count + ", agentgroup_id="
				+ agentgroup_id + ", operator_id=" + operator_id
				+ ", Selected_seats=" + Selected_seats + ", ticket_nos="
				+ ticket_nos + ", busOccurence=" + busOccurence
				+ ", permit_access_token=" + permit_access_token
				+ ", Permit_agent_id=" + Permit_agent_id + ", permit_ip="
				+ permit_ip + ", BuyerName=" + BuyerName + ", BuyerPhone="
				+ BuyerPhone + ", BuyerNRC=" + BuyerNRC + ", FromCity="
				+ FromCity + ", ToCity=" + ToCity + ", Operator_Name="
				+ Operator_Name + ", from_to=" + from_to + ", time=" + time
				+ ", classes=" + classes + ", date=" + date + ", ConfirmDate="
				+ ConfirmDate + ", ConfirmTime=" + ConfirmTime
				+ ", ExtraCityID=" + ExtraCityID + ", ExtraCityName="
				+ ExtraCityName + ", ExtraCityPrice=" + ExtraCityPrice
				+ ", ReturnDate=" + ReturnDate + ", from_intent=" + from_intent
				+ ", TicketLists=" + TicketLists + ", permit_operator_id="
				+ permit_operator_id + "]";
	}
	
	
	
}
