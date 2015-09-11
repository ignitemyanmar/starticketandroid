/* DO NOT EDIT THIS FILE - it is machine generated */
#include "SecureParam.h"
#include <iostream>
#include <ostream>
#include <sstream>
#include "string.h"
using namespace std;
/*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    getAccessTokenParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_getAccessTokenParam
  (JNIEnv * env, jobject pObj, jstring grand_type, jstring client_id, jstring client_secret, jstring username, jstring password, jstring scope, jstring state){
  		
	stringstream stream;

	const char *grand_type_c = env->GetStringUTFChars(grand_type, JNI_FALSE);
	const char *client_id_c = env->GetStringUTFChars(client_id, JNI_FALSE);
	const char *client_secret_c = env->GetStringUTFChars(client_secret, JNI_FALSE);
	const char *username_c = env->GetStringUTFChars(username, JNI_FALSE);
	const char *password_c = env->GetStringUTFChars(password, JNI_FALSE);
	const char *scope_c = env->GetStringUTFChars(scope, JNI_FALSE);
	const char *state_c = env->GetStringUTFChars(state, JNI_FALSE);

	stream << "{\"grant_type\":\""<<grand_type_c<<"\",\"client_id\":\""<<client_id_c<<"\",\"client_secret\":\""<<client_secret_c<<"\",\"username\":\""<<username_c<<"\",\"password\":\""<<password_c<<"\",\"scope\":\""<<scope_c<<"\",\"state\":\""<<state_c<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }
/*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    getSeatPlanParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_getSeatPlanParam
  (JNIEnv * env, jobject pObj, jstring token, jstring op_id, jstring t_id,jstring f_c,jstring t_c, jstring c_id, jstring d, jstring t){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);
	const char *operator_id = env->GetStringUTFChars(op_id, JNI_FALSE);
	const char *trip_id = env->GetStringUTFChars(t_id, JNI_FALSE);
	const char *from_city = env->GetStringUTFChars(f_c, JNI_FALSE);
	const char *to_city = env->GetStringUTFChars(t_c, JNI_FALSE);
	const char *class_id = env->GetStringUTFChars(c_id, JNI_FALSE);
	const char *date = env->GetStringUTFChars(d, JNI_FALSE);
	const char *times = env->GetStringUTFChars(t, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\",\"operator_id\":\""<<operator_id<<"\",\"trip_id\":\""<<trip_id<<"\",\"from_city\":\""<<from_city<<"\",\"to_city\":\""<<to_city<<"\",\"class_id\":\""<<class_id<<"\",\"date\":\""<<date<<"\",\"time\":\""<<times<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }

/*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    getTripsParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_getTripsParam
  (JNIEnv * env, jobject pObj, jstring token, jstring op_id){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);
	const char *operator_id = env->GetStringUTFChars(op_id, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\",\"operator_id\":\""<<operator_id<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }

/*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    getTimesParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_getTimesParam
  (JNIEnv * env, jobject pObj, jstring token, jstring op_id,jstring f_c,jstring t_c, jstring d){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);
	const char *operator_id = env->GetStringUTFChars(op_id, JNI_FALSE);
	const char *from_city = env->GetStringUTFChars(f_c, JNI_FALSE);
	const char *to_city = env->GetStringUTFChars(t_c, JNI_FALSE);
	const char *date = env->GetStringUTFChars(d, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\",\"operator_id\":\""<<operator_id<<"\",\"from_city\":\""<<from_city<<"\",\"to_city\":\""<<to_city<<"\",\"trip_date\":\""<<date<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }

 /*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    postSaleParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_postSaleParam(JNIEnv * env, jobject pObj, jstring token, jstring op_id, jstring ag_id, jstring name, jstring ph, jstring rmk_type, jstring rmk, jstring gp_op_id, jstring lst_seat,jstring trip_id, jstring date, jstring f_c, jstring t_c, jstring usr_id, jstring dev, jstring bking, jstring bking_usr_id, jstring is_online, jstring extra_city_id){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);
	const char *operator_id = env->GetStringUTFChars(op_id, JNI_FALSE);
	const char *agent_id = env->GetStringUTFChars(ag_id, JNI_FALSE);
	const char *username = env->GetStringUTFChars(name, JNI_FALSE);
	const char *phone = env->GetStringUTFChars(ph, JNI_FALSE);
	const char *remark_type = env->GetStringUTFChars(rmk_type, JNI_FALSE);
	const char *remark = env->GetStringUTFChars(rmk, JNI_FALSE);
	const char *group_operator_id = env->GetStringUTFChars(gp_op_id, JNI_FALSE);
	const char *seat_list = env->GetStringUTFChars(lst_seat, JNI_FALSE);
	const char *t_id = env->GetStringUTFChars(trip_id, JNI_FALSE);
	const char *trip_date = env->GetStringUTFChars(date, JNI_FALSE);
	const char *from_city = env->GetStringUTFChars(f_c, JNI_FALSE);
	const char *to_city = env->GetStringUTFChars(t_c, JNI_FALSE);
	const char *user_id = env->GetStringUTFChars(usr_id, JNI_FALSE);
	const char *device_id = env->GetStringUTFChars(dev, JNI_FALSE);
	const char *booking = env->GetStringUTFChars(bking, JNI_FALSE);
	const char *booking_user_id = env->GetStringUTFChars(bking_usr_id, JNI_FALSE);
	const char *online_agent = env->GetStringUTFChars(is_online, JNI_FALSE);
	const char *extra_id = env->GetStringUTFChars(extra_city_id, JNI_FALSE);
	
	//to change extra_id from api
	stream << "{\"access_token\":\""<<access_token<<"\",\"operator_id\":\""<<operator_id<<"\",\"agent_id\":\""<<agent_id<<"\",\"name\":\""<<username<<"\",\"phone\":\""<<phone<<"\",\"remark_type\":\""<<remark_type<<"\",\"remark\":\""<<remark<<"\",\"group_operator_id\":\""<<group_operator_id<<"\",\"seat_list\":\""<<seat_list<<"\",\"trip_id\":\""<<t_id<<"\",\"trip_date\":\""<<trip_date<<"\",\"from_city\":\""<<from_city<<"\",\"to_city\":\""<<to_city<<"\",\"user_id\":\""<<user_id<<"\",\"device_id\":\""<<device_id<<"\",\"booking\":\""<<booking<<"\",\"booking_user_id\":\""<<booking_user_id<<"\",\"online_agent\":\""<<online_agent<<"\",\"extra_city_id\":\""<<extra_id<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }


/*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    postSaleConfirmParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_postSaleConfirmParam(JNIEnv * env, jobject pObj,jstring token,jstring order_id, jstring ref_id, jstring ag_id, jstring ag_name, jstring name, jstring ph, jstring nrc, jstring rmk_type,jstring rmk, jstring ext_dest_id, jstring lst_seat, jstring cash, jstring nation, jstring or_date, jstring dev, jstring bking, jstring usr_id){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);
	const char *sale_order_no = env->GetStringUTFChars(order_id, JNI_FALSE);
	const char *reference_no = env->GetStringUTFChars(ref_id, JNI_FALSE);
	const char *agent_id = env->GetStringUTFChars(ag_id, JNI_FALSE);
	const char *agent_name = env->GetStringUTFChars(ag_name, JNI_FALSE);
	const char *buyer_name = env->GetStringUTFChars(name, JNI_FALSE);
	const char *phone = env->GetStringUTFChars(ph, JNI_FALSE);
	const char *nrc_no = env->GetStringUTFChars(nrc, JNI_FALSE);
	const char *remark_type = env->GetStringUTFChars(rmk_type, JNI_FALSE);
	const char *remark = env->GetStringUTFChars(rmk, JNI_FALSE);
	const char *extra_dest_id = env->GetStringUTFChars(ext_dest_id, JNI_FALSE);
	const char *tickets = env->GetStringUTFChars(lst_seat, JNI_FALSE);
	const char *cash_credit = env->GetStringUTFChars(cash, JNI_FALSE);
	const char *nationality = env->GetStringUTFChars(nation, JNI_FALSE);
	const char *order_date = env->GetStringUTFChars(or_date, JNI_FALSE);
	const char *device_id = env->GetStringUTFChars(dev, JNI_FALSE);
	const char *booking = env->GetStringUTFChars(bking, JNI_FALSE);
	const char *user_id = env->GetStringUTFChars(usr_id, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\",\"sale_order_no\":\""<<sale_order_no<<"\",\"reference_no\":\""<<reference_no<<"\",\"agent_id\":\""<<agent_id<<"\",\"agent_name\":\""<<agent_name<<"\",\"buyer_name\":\""<<buyer_name<<"\",\"phone\":\""<<phone<<"\",\"nrc_no\":\""<<nrc_no<<"\",\"remark_type\":\""<<remark_type<<"\",\"remark\":\""<<remark<<"\",\"extra_dest_id\":\""<<extra_dest_id<<"\",\"tickets\":\""<<tickets<<"\",\"cash_credit\":\""<<cash_credit<<"\",\"nationality\":\""<<nationality<<"\",\"order_date\":\""<<order_date<<"\",\"device_id\":\""<<device_id<<"\",\"booking\":\""<<booking<<"\",\"user_id\":\""<<user_id<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }

/*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    deleteSaleOrderParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_deleteSaleOrderParam
  (JNIEnv * env, jobject pObj, jstring token){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }

 /*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    deleteAllOrderParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_deleteAllOrderParam
  (JNIEnv * env, jobject pObj, jstring token){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }

 /*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    deleteOrderItemParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_deleteOrderItemParam
  (JNIEnv * env, jobject pObj, jstring token, jstring ids){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);
	const char *saleitem_id_list = env->GetStringUTFChars(ids, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\",\"saleitem_id_list\":\""<<saleitem_id_list<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }

/*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    getAllAgentParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_getAllAgentParam
  (JNIEnv * env, jobject pObj, jstring token, jstring op_id){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);
	const char *operator_id = env->GetStringUTFChars(op_id, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\",\"operator_id\":\""<<operator_id<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }


/*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    getBookingOrderParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_getBookingOrderParam
  (JNIEnv * env, jobject pObj, jstring token, jstring op_id, jstring d_date, jstring from, jstring to, jstring times, jstring b_code){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);
	const char *operator_id = env->GetStringUTFChars(op_id, JNI_FALSE);
	const char *departure_date = env->GetStringUTFChars(d_date, JNI_FALSE);
	const char *f_c = env->GetStringUTFChars(from, JNI_FALSE);
	const char *t_c = env->GetStringUTFChars(to, JNI_FALSE);
	const char *t = env->GetStringUTFChars(times, JNI_FALSE);
	const char *book_code = env->GetStringUTFChars(b_code, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\",\"operator_id\":\""<<operator_id<<"\",\"departure_date\":\""<<departure_date<<"\",\"from\":\""<<f_c<<"\",\"to\":\""<<t_c<<"\",\"time\":\""<<t<<"\",\"book_code\":\""<<book_code<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }

/*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    editSeatInfoParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_editSeatInfoParam
  (JNIEnv * env, jobject pObj, jstring token, jstring id, jstring date, jstring seat, jstring name, jstring ph, jstring nrc, jstring ticket){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);
	const char *trip_id = env->GetStringUTFChars(id, JNI_FALSE);
	const char *departure_date = env->GetStringUTFChars(date, JNI_FALSE);
	const char *seat_no = env->GetStringUTFChars(seat, JNI_FALSE);
	const char *customer_name = env->GetStringUTFChars(name, JNI_FALSE);
	const char *phone = env->GetStringUTFChars(ph, JNI_FALSE);
	const char *nrc_no = env->GetStringUTFChars(nrc, JNI_FALSE);
	const char *ticket_no = env->GetStringUTFChars(ticket, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\",\"trip_id\":\""<<trip_id<<"\",\"date\":\""<<departure_date<<"\",\"seat_no\":\""<<seat_no<<"\",\"customer_name\":\""<<customer_name<<"\",\"phone\":\""<<phone<<"\",\"nrc_no\":\""<<nrc_no<<"\",\"ticket_no\":\""<<ticket_no<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }

/*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    deleteTicketParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_deleteTicketParam
  (JNIEnv * env, jobject pObj, jstring token, jstring id, jstring date, jstring seat, jstring user){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);
	const char *trip_id = env->GetStringUTFChars(id, JNI_FALSE);
	const char *departure_date = env->GetStringUTFChars(date, JNI_FALSE);
	const char *seat_no = env->GetStringUTFChars(seat, JNI_FALSE);
	const char *user_id = env->GetStringUTFChars(user, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\",\"trip_id\":\""<<trip_id<<"\",\"departure_date\":\""<<departure_date<<"\",\"seat_no\":\""<<seat_no<<"\",\"user_id\":\""<<user_id<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }
  
/* >>>>>>>>>>>>>>> */
/*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    getOperatorGroupUserParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_getOperatorGroupUserParam
  (JNIEnv * env, jobject pObj, jstring token, jstring id){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);
	const char *operator_id = env->GetStringUTFChars(id, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\",\"operator_id\":\""<<operator_id<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }

 /* Class:    com_ignite_mm_ticketing_application_SecureParam
 * Method:    getExtraDestinationParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_getExtraDestinationParam
  (JNIEnv * env, jobject pObj, jstring token){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }

/*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    getNotiBookingParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_getNotiBookingParam
  (JNIEnv * env, jobject pObj, jstring token, jstring date){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);
	const char *trip_date = env->GetStringUTFChars(date, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\",\"date\":\""<<trip_date<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }

/*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    getCitybyOperatorParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_getCitybyOperatorParam
  (JNIEnv * env, jobject pObj, jstring token, jstring id){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);
	const char *operator_id = env->GetStringUTFChars(id, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\",\"operator_id\":\""<<operator_id<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }

/*
 * Class:     com_ignite_mm_ticketing_application_SecureParam
 * Method:    getTimebyOperatorParam
 * Signature: ()Ljava/lang/String;
 */
 JNIEXPORT jstring JNICALL Java_com_ignite_mm_ticketing_application_SecureParam_getTimebyOperatorParam
  (JNIEnv * env, jobject pObj, jstring token, jstring id){
  		
	stringstream stream;

	const char *access_token = env->GetStringUTFChars(token, JNI_FALSE);
	const char *operator_id = env->GetStringUTFChars(id, JNI_FALSE);

	stream << "{\"access_token\":\""<<access_token<<"\",\"operator_id\":\""<<operator_id<<"\"}";

	string result = stream.str();

	return env->NewStringUTF(result.c_str());
  }
  
