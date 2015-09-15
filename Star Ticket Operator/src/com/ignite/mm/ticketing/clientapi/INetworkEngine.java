package com.ignite.mm.ticketing.clientapi;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * {@link #INetworkEngine} is the interface to make methods for API's route and parameters
 * <p>
 * ** Star Ticket Operator App is used to sell bus tickets via online. 
 * @version 2.0 
 * @author Su Wai Phyo (Ignite Software Solutions)
 * <p>
 * Last Modified : 14/Sept/2015
 * <p>
 * Last ModifiedBy : Saw Maine K
 */
public interface INetworkEngine {
	
	@FormUrlEncoded
	@POST("/oauth/access_token")
	void getAccessToken(@Field("param") String param, Callback<Response> callback);
	
	@GET("/seatplan")
	void getItems(@Query("param") String param, Callback<Response> callback);

	@GET("/city")
	void getAllCity(@Query("access_token") String access_token, Callback<Response> callback);
	
	@GET("/trips")
	void getTrips(@Query("param") String param, Callback<Response> callback);
	
	@GET("/time")
	void getAllTime(@Query("param") String param, Callback<Response> callback);
			 
	@GET("/operator")
	void getAllOperator(@Query("access_token")String access_token, Callback<Response> callback);
	
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
	
	@FormUrlEncoded
	@POST("/closeseat")
	void postCloseSeat(@Field("access_token")String access_token, @Field("date") String date, @Field("trip_id") String trip_id, @Field("remark") String remark ,@Field("seats") String seats,@Field("agent_id") String agent_id, Callback<Response> callback);
	
	@FormUrlEncoded
	@POST("/openseat")
	void postOpenSeat(@Field("access_token")String access_token, @Field("date") String date, @Field("trip_id") String trip_id,@Field("seats") String seats, Callback<Response> callback);
	

}
