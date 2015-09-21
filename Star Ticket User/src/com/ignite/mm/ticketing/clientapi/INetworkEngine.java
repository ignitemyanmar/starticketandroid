package com.ignite.mm.ticketing.clientapi;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

import com.ignite.mm.ticketing.application.LoginUser;
import com.ignite.mm.ticketing.sqlite.database.model.AccessToken;
import com.ignite.mm.ticketing.sqlite.database.model.ForgotPassword;
import com.ignite.mm.ticketing.sqlite.database.model.Loyalty;
import com.ignite.mm.ticketing.sqlite.database.model.MyOrder;
import com.ignite.mm.ticketing.sqlite.database.model.Operator;
import com.ignite.mm.ticketing.sqlite.database.model.Promotion;
import com.ignite.mm.ticketing.sqlite.database.model.ThreeDaySale;

/**
 * {@link #INetworkEngine} is the interface to make methods for API's route and parameters
 * <p>
 * ** Star Ticket App is used to purchase bus tickets via online. 
 * Pay @Convenient Stores(City Express, ABC, G&G, Sein Gay Har-parami, etc.) in Myanmar or
 * Pay via (MPU, Visa, Master) 
 * @author Su Wai Phyo (Ignite Software Solutions), 
 * Last Modified : 04/Sept/2015, 
 * Last ModifiedBy : Su Wai Phyo
 * @version 1.0 
 */
public interface INetworkEngine {
	
	@GET("/seatplan")
	void getItems(@Query("param") String param, Callback<Response> callback);

	@GET("/city")
	void getAllCity(@Query("access_token") String access_token, Callback<Response> callback);
	
	@GET("/trips")
	void getTrips(@Query("param") String param, Callback<Response> callback);
	
	@GET("/time")
	void getAllTime(@Query("param") String param, Callback<Response> callback);
			 
	@GET("/api/operatorlist")
	void getAllOperator(@Query("access_token")String access_token, Callback<List<Operator>> callback);
	
	@FormUrlEncoded
	@POST("/sale/{id}/delete")
	void deleteSaleOrder( @Field("param")String param, @Path("id") String id,Callback<Response> callback);
	
	@GET("/agent")
	void getAllAgent(@Query("param")String param, Callback<Response> callback);
	
	@GET("/sale/order")
	void getBookingOrder(@Query("param") String param, Callback<Response> callback);
	
	@FormUrlEncoded
	@POST("/sale/credit/delete/{id}")
	void deleteAllOrder(@Field("param")String param, @Path("id") String id,Callback<Response> callback);

	@FormUrlEncoded
	@POST("/sale/credit/cancelticket")
	void deleteOrderItem(@Field("param")String param, Callback<Response> callback);

	@FormUrlEncoded
	@POST("/report/customer/update")
	void editSeatInfo(@Field("param")String param, Callback<Response> callback);
	
	@FormUrlEncoded
	@POST("/ticket_delete")
	void deleteTicket(@Field("param")String param, Callback<Response> callback);
	
	@GET("/operatorgroup")
	void getOperatorGroupUser(@Query("param") String param, Callback<Response> callback);
	
	@GET("/extra_destination/{id}")
	void getExtraDestination(@Query("param")String param ,@Path("id")String id, Callback<Response> callback);

	@GET("/booking/notify")
	void getNotiBooking(@Query("param")String param, Callback<Response> callback);
	
	@GET("/citiesbyoperator")
	void getCitybyOperator(@Query("param") String param, Callback<Response> callback);
	
	@GET("/timesbyoperator")
	void getTimebyOperator(@Query("param") String param, Callback<Response> callback);

/*	@FormUrlEncoded
	@POST("/user-register")
	void postRegister(
			@Field("name") String first_name,
			@Field("last_name") String last_name,
			@Field("email") String email,
			@Field("password") String password,
			@Field("phone") String phone,
			@Field("nrc") String nrc,
			@Field("address") String address,
			@Field("type") String type,
			Callback<LoginUser> callback);*/
	
	/*@FormUrlEncoded
	@POST("/user-login")
	void postLogin(
			@Field("email") String first_name,
			@Field("password") String last_name,
			Callback<LoginUser> callback);*/
	
	@FormUrlEncoded
	@POST("/oauth/access_token") 
	void getAccessToken(@Field("grant_type") String grant_type,
			@Field("client_id") String client_id,
			@Field("client_secret") String client_secret,
			@Field("username") String username,
			@Field("password") String password, 
			@Field("scope") String scope,
			@Field("state") String state, Callback<AccessToken> callback);
	
	@GET("/api/gettrips")
	void getPermission(@Query("access_token") String token, 
			@Query("operator_id") String operatorId, Callback<Response> callback);
	
	@GET("/sale/order")
	void getBooking(@Query("access_token") String token,
			@Query("operator_id") String operator_id,
			@Query("departure_date") String departure_date,
			@Query("from") String from,
			@Query("to") String to,
			@Query("time") String time,
			@Query("book_code") String book_code, Callback<Response> callback);
	
	@GET("/api/olsalepermittrips")
	void getOnlineSalePermitTrip(@Query("access_token") String access_token,
			@Query("operator_id") String operator_id, Callback<Response> callback);
	
	@GET("/api/threedayssale")
	void getThreeDaySales(@Query("access_token") String token, 
			@Query("code_no") String code_no, 
			@Query("offset") String offset,
			@Query("limit") String limit, Callback<List<ThreeDaySale>> callback);
	
/*	@GET("/api/myorderlist")
	void getMyOrders(@Query("access_token") String token, 
			@Query("code_no") String code_no, 
			@Query("offset") String offset,
			@Query("limit") String limit, Callback<List<MyOrder>> callback);*/
	
	@FormUrlEncoded
	@POST("/api/salecomfirm")
	void postOnlineSaleDB(
			@Field("sale_order_no") String sale_order_no,
			@Field("operator_id") String operator_id,
			@Field("user_code_no") String user_code_no,
			@Field("access_token") String access_token, 
			@Field("extra_name") String extra_name,
			@Field("loyalty_phone") String loyalty_phone,
			@Field("loyalty_name") String loyalty_name,
			@Field("loyalty_address") String loyalty_address,
			@Field("loyalty_township") String loyalty_township,
			@Field("use_points") String use_points,
			@Field("use_gift_money") String use_gift_money,
			@Field("promotion_code") String promotion_code,
			@Field("payment") String payment,
			@Field("agentgroup_id") String agentgroup_id,
			@Field("sale_booking") String sale_booking,
			@Field("payment_type") String payment_type, /*Cash on Shop | Pay with Online | Cash on Delivery */
			@Field("starticket_no") String starticket_no,
			@Field("round_trip") String round_trip,
			Callback<Response> callback);
	
	@FormUrlEncoded
	@POST("/api/salecomfirm")
	void postOnlineSaleDB(
			@Field("sale_order_no") String sale_order_no,
			@Field("operator_id") String operator_id,
			@Field("user_code_no") String user_code_no,
			@Field("sale_booking") Integer sale_booking,
			@Field("access_token") String access_token, 
			Callback<Response> callback);
	
	@GET("/api/from_city")
	void getFromCities(
			@Query("access_token") String access_token,
			@Query("user_app") String user_app, Callback<Response> callback);
	
	@GET("/api/to_cities")
	void getToCities(
			@Query("access_token") String access_token,
			@Query("from") String from,
			@Query("round_trip") String round_trip, Callback<Response> callback);
	
	@GET("/api/times")
	void getTimes(@Query("access_token") String access_token, Callback<Response> callback);
	
	@GET("/api/timesbyfrom_to")
	void getTimesByTrip(@Query("access_token") String access_token,
			@Query("from_city") String from_city,
			@Query("to_city") String to_city, Callback<Response> callback);
	
	@FormUrlEncoded
	@POST("/api/search")
	void postSearch(@Field("from") String from, 
			@Field("to") String to, 
			@Field("date") String date,
			@Field("time") String time,
			@Field("access_token") String access_token, 
			@Field("round_trip") String round_trip, Callback<Response> callback);
	
	@GET("/api/bookingsearch")
	void getBookingByCodeNoPhone(@Query("access_token") String access_token,
			@Query("search") String codeOrPhone, Callback<Response> callback);
	
	@GET("/api/bookingsearch")
	void getBookingListAll(@Query("access_token") String access_token, Callback<Response> callback);
	
	@GET("/api/userbooking/{id}")
	void getBookingListByUser(@Query("access_token") String access_token,
			@Query("order_id") String order_id,
			@Query("trip_id") String trip_id,
			@Path("id") String id, Callback<Response> callback);
	
	@GET("/api/bookingdelete/{id}")
	void getBookingDeleteByOrderID(@Query("access_token") String access_token,
			@Path("id") String id, Callback<Response> callback);
	
	@FormUrlEncoded
	@POST("/api/customerupdate")
	void editSeatInfo(
			@Field("access_token") String access_token, 
			@Field("trip_id") String trip_id, 
			@Field("departure_date") String departure_date,
			@Field("customer_name") String name,
			@Field("phone") String phone,
			@Field("nrc_no") String nrc_no,
			@Field("ticket_no") String ticket_no,
			@Field("seat_no") String seat_no,
			Callback<Response> callback);
	
	@FormUrlEncoded
	@POST("/api/soldseat/delete")
	void deleteSeat(
			@Field("access_token") String access_token, 
			@Field("trip_id") String trip_id, 
			@Field("departure_date") String departure_date,
			@Field("seat_no") String seat_no,
			Callback<Response> callback);
	
	@FormUrlEncoded
	@POST("/api/user/register")
	void postRegister(
			@Field("name") String name,
			@Field("email") String email,
			@Field("password") String password,
			@Field("phone") String phone,
			@Field("address") String address,
			@Field("username") String username,
			Callback<LoginUser> callback);
	
	@FormUrlEncoded
	@POST("/api/user/update/{id}")
	void postEditProfile(
			@Field("name") String name,
			@Field("email") String email,
			@Field("password") String password,
			@Field("phone") String phone,
			@Field("address") String address, 
			@Field("access_token") String access_token,
			@Field("current_password") String current_password,
			@Field("username") String username,
			@Path("id") String id, Callback<LoginUser> callback);
	
	@GET("/promotion")
	void GetPromotion(Callback<List<Promotion>> callback);
	
	@GET("/api/forgotpassword")
	void GetForgotPassword(
			@Query("email") String email, Callback<ForgotPassword> callback);
	
	@GET("/api/resetpassword")
	void GetResetPassword(
			@Query("email") String email,
			@Query("code") String code,
			@Query("password") String password, Callback<ForgotPassword> callback);
	
	@FormUrlEncoded
	@POST("/loyalty/check")
	void postLoyalty(
			@Field("phone") String phone,
			@Field("total_amount") String total_amount, 
			@Field("payment_method_id") String payment_method_id,
			@Field("agentgroup_id") String agentgroup_id,
			@Field("operator_id") String operator_id,
			Callback<Loyalty> callback);
	
	@GET("/loyalty/{id}")
	void getLoyaltyByUser(
			@Path("id") String id, Callback<Loyalty> callback);
	
	@GET("/api/currencyrate/USD")
	void getCurrencyRate(Callback<Double> callback);
	
}
