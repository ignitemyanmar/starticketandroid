package com.ignite.mdm.ticketing.clientapi;

import com.google.gson.JsonObject;
import com.ignite.mdm.ticketing.sqlite.database.model.AccessToken;
import com.ignite.mdm.ticketing.sqlite.database.model.AgentDeposit;
import com.ignite.mdm.ticketing.sqlite.database.model.AgentSeatsBooking;
import com.ignite.mdm.ticketing.sqlite.database.model.Delivery;
import com.ignite.mdm.ticketing.sqlite.database.model.InvoiceDetail;
import com.ignite.mdm.ticketing.sqlite.database.model.Invoices;
import com.ignite.mdm.ticketing.sqlite.database.model.KhoneAtList;
import com.ignite.mdm.ticketing.sqlite.database.model.NotiCounts;
import com.ignite.mdm.ticketing.sqlite.database.model.Operator;
import com.ignite.mdm.ticketing.sqlite.database.model.OutstandingBooking;
import com.ignite.mdm.ticketing.sqlite.database.model.PaymentResponse;
import com.ignite.mdm.ticketing.sqlite.database.model.StarTicketAgents;
import com.ignite.mdm.ticketing.sqlite.database.model.ThreeDaySale;
import com.ignite.mm.ticketing.application.LoginUser;
import java.util.List;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface INetworkEngine {

  //----------------------------------- Get Method --------------------------------------------------------------

  @GET("/api/noti-counts") void getNotiCounts(Callback<NotiCounts> callback);

  @GET("/api/agentdeposit") void getAgentDeposit(@Query("access_token") String access_token,
      @Query("user_id") String user_id, @Query("start_date") String start_date,
      @Query("end_date") String end_date, Callback<AgentDeposit> callback);

  @GET("/api/toursbooking/{id}/change-status") void changeStatus(@Query("status") String status,
      @Path("id") String id, Callback<JsonObject> callback);

  @GET("/api/toursbooking") void getAgentSeatsBooking(Callback<List<AgentSeatsBooking>> callback);

  @GET("/seatplan") void getItems(@Query("param") String param, Callback<Response> callback);

  @GET("/city") void getAllCity(@Query("access_token") String access_token,
      Callback<Response> callback);

  @GET("/trips") void getTrips(@Query("param") String param, Callback<Response> callback);

  @GET("/time") void getAllTime(@Query("param") String param, Callback<Response> callback);

  @GET("/api/operatorlist") void getAllOperator(@Query("access_token") String access_token,
      Callback<List<Operator>> callback);

  @GET("/agent") void getAllAgent(@Query("param") String param, Callback<Response> callback);

  @GET("/api/agent-balance") void getAgentBalance(@Query("param") String param,
      Callback<Response> callback);

  @GET("/api/agentpayments") void getAgentPayments(@Query("param") String param,
      Callback<PaymentResponse> callback);

  @GET("/sale/order") void getBookingOrder(@Query("param") String param,
      Callback<Response> callback);

  @GET("/operatorgroup") void getOperatorGroupUser(@Query("param") String param,
      Callback<Response> callback);

  @GET("/extra_destination/{id}") void getExtraDestination(@Query("param") String param,
      @Path("id") String id, Callback<Response> callback);

  @GET("/booking/notify") void getNotiBooking(@Query("param") String param,
      Callback<Response> callback);

  @GET("/citiesbyoperator") void getCitybyOperator(@Query("param") String param,
      Callback<Response> callback);

  @GET("/timesbyoperator") void getTimebyOperator(@Query("param") String param,
      Callback<Response> callback);

  @GET("/api/gettrips") void getPermission(@Query("access_token") String token,
      @Query("operator_id") String operatorId, Callback<Response> callback);

  @GET("/sale/order") void getBooking(@Query("access_token") String token,
      @Query("operator_id") String operator_id, @Query("departure_date") String departure_date,
      @Query("from") String from, @Query("to") String to, @Query("time") String time,
      @Query("book_code") String book_code, Callback<Response> callback);

  @GET("/api/olsalepermittrips") void getOnlineSalePermitTrip(
      @Query("access_token") String access_token, @Query("operator_id") String operator_id,
      Callback<Response> callback);

  @GET("/api/threedayssale") void getThreeDaySales(@Query("access_token") String token,
      @Query("date_type") int date_type, @Query("code_no") String code_no,
      @Query("start_date") String start_date, @Query("end_date") String end_date,
      Callback<List<ThreeDaySale>> callback);

  @GET("/outstandingsoldtickets") void getOutstandingSales(@Query("access_token") String token,
      @Query("date_type") int date_type, @Query("agent_code_no") String code_no,
      @Query("end_date") String end_date, Callback<List<ThreeDaySale>> callback);

  @GET("/api/threedayssale") void getThreeDaySales(@Query("access_token") String token,
      @Query("code_no") String code_no, @Query("start_date") String start_date,
      @Query("end_date") String end_date, Callback<List<ThreeDaySale>> callback);

  @GET("/api/from_cities") void getFromCities(@Query("access_token") String access_token,
      @Query("operator_id") String operatorId, Callback<Response> callback);

  @GET("/api/to_cities") void getToCities(@Query("access_token") String access_token,
      @Query("operator_id") String operatorId, Callback<Response> callback);

  @GET("/api/times") void getTimes(@Query("access_token") String access_token,
      Callback<Response> callback);

  @GET("/api/timesbyfrom_to") void getTimesByTrip(@Query("access_token") String access_token,
      @Query("from_city") String from_city, @Query("to_city") String to_city,
      @Query("operator_id") String operatorId, Callback<Response> callback);

  @GET("/api/bookingsearch") void getBookingByCodeNoPhone(
      @Query("access_token") String access_token, @Query("search") String codeOrPhone,
      Callback<Response> callback);

  @GET("/api/bookingsearch") void getBookingListAll(@Query("access_token") String access_token,
      Callback<Response> callback);

  @GET("/api/userbooking/{id}") void getBookingListByUser(
      @Query("access_token") String access_token, @Query("order_id") String order_id,
      @Query("trip_id") String trip_id, @Path("id") String id, Callback<Response> callback);

  @GET("/api/bookingdelete/{id}") void getBookingDeleteByOrderID(
      @Query("access_token") String access_token, @Path("id") String id,
      Callback<Response> callback);

  @GET("/outstandingsoldtickets") void getOutstandings(@Query("param") String param,
      Callback<OutstandingBooking> callback);

  //http://starticketmyanmar.com/outstandingsoldtickets?
  /*@GET("/api/salebytripdate")
  void getSaleByTripDate(@Query("access_token") String access_token,
			@Query("code_no") String code_no, 
			@Query("operator_id") String operator_id, Callback<List<KhoneAtList>> callback);*/

  @GET("/paymentinvoices") void getInvoices(@Query("param") String access_token,
      Callback<List<Invoices>> callback);

  @GET("/paymentinvoices/detail") void getInvoiceDetail(@Query("param") String access_token,
      Callback<InvoiceDetail> callback);


  @GET("/api/khone-up") void getKhoneAtList(@Query("operator_id") String operator_id,
      Callback<List<KhoneAtList>> callback);

  @GET("/api/starticketagents") void getStarTicketAgents(@Query("user_id") String user_id,
      Callback<List<StarTicketAgents>> callback);

  @GET("/api/delivery-list") void getDeliveryList(@Query("access_token") String access_token,
      Callback<List<Delivery>> callback);

  //------------------------------------------------------ Post Method ---------------------------------------

  @FormUrlEncoded @POST("/api/toursbooking") void postTourBooking(@Field("user_id") String user_id,
      @Field("trip_id") String trip_id, @Field("departure_date") String departure_date,
      @Field("total_seat") String total_seat, @Field("phone_no") String phone_no,
      @Field("extra_city") String extra_city, Callback<JsonObject> callback);

  @FormUrlEncoded @POST("/api/delivery/{orderid}/delete") void deleteDelivery(
      @Field("access_token") String access_token, @Path("orderid") String orderid,
      @Field("user_id") String user_id, Callback<Response> callback);

  @FormUrlEncoded @POST("/sale/{id}/delete") void deleteSaleOrder(@Field("param") String param,
      @Path("id") String id, Callback<Response> callback);

  @FormUrlEncoded @POST("/sale/credit/delete/{id}") void deleteAllOrder(
      @Field("param") String param, @Path("id") String id, Callback<Response> callback);

  @FormUrlEncoded @POST("/sale/credit/cancelticket") void deleteOrderItem(
      @Field("param") String param, Callback<Response> callback);

  @FormUrlEncoded @POST("/report/customer/update") void editSeatInfo(@Field("param") String param,
      Callback<Response> callback);

  @FormUrlEncoded @POST("/ticket_delete") void deleteTicket(@Field("param") String param,
      Callback<Response> callback);

  @FormUrlEncoded @POST("/user-register") void postRegister(@Field("name") String first_name,
      @Field("last_name") String last_name, @Field("email") String email,
      @Field("password") String password, @Field("phone") String phone, @Field("nrc") String nrc,
      @Field("address") String address, @Field("type") String type, Callback<LoginUser> callback);

	/*@FormUrlEncoded
  @POST("/user-login")
	void postLogin(
			@Field("email") String first_name,
			@Field("password") String last_name,
			Callback<LoginUser> callback);*/

  @FormUrlEncoded @POST("/oauth/access_token") void getAccessToken(
      @Field("grant_type") String grant_type, @Field("client_id") String client_id,
      @Field("client_secret") String client_secret, @Field("username") String username,
      @Field("password") String password, @Field("scope") String scope,
      @Field("state") String state, Callback<AccessToken> callback);

  @FormUrlEncoded @POST("/api/salecomfirm") void postOnlineSaleDB(
      @Field("sale_order_no") String sale_order_no, @Field("operator_id") String operator_id,
      @Field("user_code_no") String user_code_no, @Field("access_token") String access_token,
      @Field("extra_name") String extra_name, Callback<Response> callback);

  @FormUrlEncoded @POST("/api/salecomfirm") void postOnlineSaleDB(
      @Field("sale_order_no") String sale_order_no, @Field("operator_id") String operator_id,
      @Field("user_code_no") String user_code_no, @Field("sale_booking") Integer sale_booking,
      @Field("access_token") String access_token, @Field("extra_name") String extra_name,
      @Field("starticket_no") String starticket_no, Callback<Response> callback);

  @FormUrlEncoded @POST("/api/search") void postSearch(@Field("from") String from,
      @Field("to") String to, @Field("date") String date, @Field("time") String time,
      @Field("access_token") String access_token, @Field("operator_id") String operator_id,
      Callback<Response> callback);

  @FormUrlEncoded @POST("/api/customerupdate") void editSeatInfo(
      @Field("access_token") String access_token, @Field("trip_id") String trip_id,
      @Field("departure_date") String departure_date, @Field("customer_name") String name,
      @Field("phone") String phone, @Field("nrc_no") String nrc_no,
      @Field("ticket_no") String ticket_no, @Field("seat_no") String seat_no,
      Callback<Response> callback);

  @FormUrlEncoded @POST("/api/soldseat/delete") void deleteSeat(
      @Field("access_token") String access_token, @Field("trip_id") String trip_id,
      @Field("departure_date") String departure_date, @Field("seat_no") String seat_no,
      Callback<Response> callback);

  @FormUrlEncoded @POST("/api/delivery-complete/{orderid}") void postCompleteDelivery(
      @Field("access_token") String access_token, @Field("password") String password,
      @Path("orderid") String orderid, Callback<Response> callback);

  @FormUrlEncoded @POST("/closeseat") void postCloseSeat(@Field("access_token") String access_token,
      @Field("date") String date, @Field("trip_id") String trip_id, @Field("remark") String remark,
      @Field("seats") String seats, @Field("agent_id") String agent_id,
      Callback<Response> callback);

  @FormUrlEncoded @POST("/openseat") void postOpenSeat(@Field("access_token") String access_token,
      @Field("date") String date, @Field("trip_id") String trip_id, @Field("seats") String seats,
      Callback<Response> callback);
}
