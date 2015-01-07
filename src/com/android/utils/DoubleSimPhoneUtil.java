package com.android.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-24
 * Time: 下午5:15
 * To change this template use File | Settings | File Templates.
 */
public class DoubleSimPhoneUtil {
    /**
     * 判断是否为双卡双待机
     * 调用双卡双待机特有方法，不抛异常则为双卡机
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static boolean isGemini(Context context) {
        if (context == null) {
            return false;
        }
        try {

            String imei = getImeiBySlot(context, 1);



            if (imei == null || imei.equals("")) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 双卡双待手机获取imei号， 双卡双待手机能获取2个
     *
     * @param context slotID card1:0; card2:1
     * @throws Exception
     */
    public static String getImeiBySlot(Context context, int slotID) {

        String imei = "";

        if (context == null) {
            return "";
        }
        if (slotID < 0 || slotID > 1) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            Class<?> mLoadClass = Class.forName("android.telephony.TelephonyManager");

            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getImei = mLoadClass.getMethod("getDeviceIdGemini", parameter);

            Object[] obParameter = new Object[1];

            Object ob_imei = null;

            obParameter[0] = slotID;
            ob_imei = getImei.invoke(telephonyManager, obParameter);

            if (ob_imei != null) {
                imei = ob_imei.toString();
            }
        } catch (Exception e) {
//			T.warn("DoubleSimPhoneUtil:007:" + e.toString());
        }
        return imei;
    }


    /**
     * 根据运营商选择最优卡槽对应的sim卡
     *
     * @param context
     * @return
     */
    public static int getSlotByOperator(Context context) {

        int slotID = 0;

        if (context == null) {
            return 0;
        }
        try {
            //获取准备好的卡
            int readyRlot = getSlotIdReady(context);



            if (readyRlot != 2) {   //readyRlot = 2   表示卡1卡2都准备好了
                return readyRlot;
            }

            boolean isSlot1LT = false;    //卡1 是联通？
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            Class<?> mLoadClass = Class.forName("android.telephony.TelephonyManager");

            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimOperatorGemini = mLoadClass.getMethod("getSimOperatorGemini", parameter);

            Object[] obParameter = new Object[1];
            obParameter[0] = 0;
            Object object = getSimOperatorGemini.invoke(telephonyManager, obParameter);

            if (object != null) {
                String operator = object.toString();
                if (operator.equals("46000") || operator.equals("46002")) {//移动
                    obParameter[0] = 1;
                    object = getSimOperatorGemini.invoke(telephonyManager, obParameter);
                    if (object != null) {
                        operator = object.toString();
                        if (operator.equals("46000") || operator.equals("46002")) {   //移动
                            slotID = getDefaultSim();    //卡1 卡2 都是移动 卡 则优先选择用户默认的发短信的卡
                            return slotID;
                        }
                    }
                    return 0;
                } else if (operator.equals("46001")) {    //联通
                    isSlot1LT = true;
                }
            }
            obParameter[0] = 1;
            object = getSimOperatorGemini.invoke(telephonyManager, obParameter);
            if (object != null) {
                String operator = object.toString();
                if (operator.equals("46000") || operator.equals("46002")) {   //移动
                    return 1;

                } else if (operator.equals("46001")) {    //联通
                    if (isSlot1LT) {
                        slotID = getDefaultSim();    //卡1 卡2 都是联通 卡 则优先选择用户默认的发短信的卡
                    } else {
                        return 1;
                    }

                } else {          //电信
                    if (isSlot1LT) {  //卡1是联通，优先选 卡1
                        return 0;
                    } else {
                        slotID = getDefaultSim();    //卡1 卡2 都是 电信则优先选择用户默认的发短信的卡
                    }
                }
            }
        } catch (Exception e) {

        }
        return slotID;
    }


    /**
     * 获取双卡双待手机当前默认选择的卡
     * slotId   0表示 卡1或者 每次询问  ； 1表示 卡2
     */
    public static int getDefaultSim() {
        int slotID = 0;
        try {
            Class<?> smsManagerClass = Class.forName("android.telephony.SmsManager");

            Method method = smsManagerClass.getMethod("getDefault", new Class[]{});

            Object smsManager = method.invoke(smsManagerClass, new Object[]{});

            Method getDefaultSim = smsManagerClass.getDeclaredMethod("getDefaultSim", new Class[]{});

            getDefaultSim.setAccessible(true);

            Object object = getDefaultSim.invoke(smsManager, new Object[]{});

            if (object != null) {
                slotID = Integer.parseInt(object.toString());
            }
        } catch (Exception e) {

        }
        return slotID;
    }


    /**
     * 双卡双待手机根据卡槽ID 获取imsi号， 能获取2个
     *
     * @param context 卡槽ID slotID card1:0; card2:1
     *                <p/>
     *                用于判断是否为双卡双待手机，异常抛出由上层处理
     */
    public static String getImsiBySlot(Context context, int slotID) throws Exception {
        String imsi = "";
        if (context == null) {
            return "";
        }

        if (slotID < 0 || slotID > 1) {
            return "";
        }

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        Class<?> mLoadClass = Class.forName("android.telephony.TelephonyManager");

        Class<?>[] parameter = new Class[1];
        parameter[0] = int.class;
        Method getSubscriberIdGemini = mLoadClass.getMethod("getSubscriberIdGemini", parameter);

        Object ob_imsi = null;
        Object[] obParameter = new Object[1];
        obParameter[0] = slotID;
        ob_imsi = getSubscriberIdGemini.invoke(telephonyManager, obParameter);

        if (ob_imsi != null) {
            imsi = ob_imsi.toString();

        }

        return imsi;
    }

    /**
     * 获取当前准备好的卡，  0 ：卡1；   1：卡2；  -1都没准备好；  2 都准备好了
     *
     * @param context
     * @return
     */
    public static int getSlotIdReady(Context context) {

        boolean isSim1Ready = isSimReady(context, 0);
        boolean isSim2Ready = isSimReady(context, 1);

        if (isSim1Ready && isSim2Ready) {  //卡1准备好，卡2没准备好则使用卡2
            return 2;

        } else if (isSim1Ready && !isSim2Ready) {  //卡1准备好，卡2没准备好则使用卡2
            return 0;

        } else if (!isSim1Ready && isSim2Ready) {  //卡2准备好，卡1没准备好则使用卡1
            return 1;

        } else if (!isSim1Ready && !isSim2Ready) {  //卡2,卡1 都没准备
            return -1;
        }

        return 0;
    }

    /**
     * sim卡是否已经准备好 ，排除无卡和飞行模式
     *
     * @param context 卡槽ID slotID card1:0; card2:1
     * @return
     */
    public static boolean isSimReady(Context context, int slotID) {
        boolean isReady = false;
        if (context == null) {
            return false;
        }

        if (slotID < 0 || slotID > 1) {
            return false;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            Class<?> mLoadClass = Class.forName("android.telephony.TelephonyManager");

            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimStateGemini = mLoadClass.getMethod("getSimStateGemini", parameter);

            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimStateGemini.invoke(telephonyManager, obParameter);

            if (ob_phone != null) {
                int simState = Integer.parseInt(ob_phone.toString());
                if (simState == TelephonyManager.SIM_STATE_READY) {
                    isReady = true;
                }

            }
        } catch (Exception e) {

        }

        return isReady;
    }


    /**
     * 双卡双待手机根据卡槽ID 获取手机号码， 能获取2个
     *
     * @param context 卡槽ID slotID card1:0; card2:1
     */
    public static String getPhoneBySlot(Context context, int slotID) {
        String phone = "";
        if (context == null) {
            return "";
        }

        if (slotID < 0 || slotID > 1) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            Class<?> mLoadClass = Class.forName("android.telephony.TelephonyManager");

            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getLine1NumberGemini = mLoadClass.getMethod("getLine1NumberGemini", parameter);

            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getLine1NumberGemini.invoke(telephonyManager, obParameter);

            if (ob_phone != null) {
                phone = ob_phone.toString();

            }
        } catch (Exception e) {

        }
        return phone;
    }


    /**
     * 双卡双待手机根据SIM卡ID获取imsi号， 能获取2个
     *
     * @param context
     * @param sim_id  SIM卡ID
     * @return
     */
    public static String getImsiBySimID(Context context, long sim_id) {
        String imsi = "";

        if (context == null || sim_id < 1) {
            return "";
        }
        try {
            SimCardInfo info = getSimInfoBySlot(context, 0);  //卡1
            if (info != null) {
                if (info.mSimId == sim_id) {
                    imsi = getImsiBySlot(context, 0);
                    return imsi;
                }
            }

            info = getSimInfoBySlot(context, 1);  //卡2
            if (info != null) {
                if (info.mSimId == sim_id) {
                    imsi = getImsiBySlot(context, 1);
                    return imsi;
                }
            }
        } catch (Exception e) {

        }
        return imsi;
    }

    /**
     * 根据卡槽ID 获取对应sim卡信息    0：卡1  1：卡2
     *
     * @param context
     * @param slotID
     * @return The SIM-Info, maybe null
     */
    public static SimCardInfo getSimInfoBySlot(Context context, int slotID) {
        if (context == null || slotID < 0) {
            return null;
        }

        try {
            Cursor cursor = context.getContentResolver().query(SimInfo.CONTENT_URI,
                    null, SimInfo.SLOT + "=?",
                    new String[]{String.valueOf(slotID)}, null);
            try {
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        return fromCursor(cursor);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (Exception e) {

        }
        return null;
    }


    /**
     * 获取sim卡信息
     *
     * @param cursor
     * @return
     */
    public static SimCardInfo fromCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        SimCardInfo info = new SimCardInfo();
        try {
            info.mSimId = cursor.getLong(cursor
                    .getColumnIndexOrThrow(DoubleSimPhoneUtil.SimInfo._ID));
            info.mICCId = cursor.getString(cursor
                    .getColumnIndexOrThrow(SimInfo.ICC_ID));
            info.mDisplayName = cursor.getString(cursor
                    .getColumnIndexOrThrow(SimInfo.DISPLAY_NAME));
            info.mNumber = cursor.getString(cursor
                    .getColumnIndexOrThrow(SimInfo.NUMBER));
            info.mDispalyNumberFormat = cursor.getInt(cursor
                    .getColumnIndexOrThrow(SimInfo.DISPLAY_NUMBER_FORMAT));
            info.mColor = cursor.getInt(cursor
                    .getColumnIndexOrThrow(SimInfo.COLOR));
            info.mDataRoaming = cursor.getInt(cursor
                    .getColumnIndexOrThrow(SimInfo.DATA_ROAMING));
            info.mSlot = cursor.getInt(cursor
                    .getColumnIndexOrThrow(SimInfo.SLOT));
        } catch (Exception e) {

        }
        return info;
    }

    static class SimCardInfo {
        public long mSimId;
        public String mICCId = "";
        public String mDisplayName = "";
        public String mNumber = "";
        public int mDispalyNumberFormat = 1;
        public int mColor;
        public int mDataRoaming = 0;
        public int mSlot = -1;

    }


    public static final class SimInfo implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://telephony/siminfo");

        public static final String DEFAULT_SORT_ORDER = "name ASC";
        public static final String ICC_ID = "icc_id";
        public static final String DISPLAY_NAME = "display_name";
        public static final int DEFAULT_NAME_MIN_INDEX = 01;
        public static final int DEFAULT_NAME_MAX_INDEX = 99;
        public static final String NUMBER = "number";

        /**
         * 0:none, 1:the first four digits, 2:the last four digits.
         */
        public static final String DISPLAY_NUMBER_FORMAT = "display_number_format";
        public static final int DISPALY_NUMBER_NONE = 0;
        public static final int DISPLAY_NUMBER_FIRST = 1;
        public static final int DISPLAY_NUMBER_LAST = 2;
        public static final int DISLPAY_NUMBER_DEFAULT = DISPLAY_NUMBER_FIRST;

        /**
         * Eight kinds of colors. 0-7 will represent the eight colors.
         * Default value: any color that is not in-use.
         */
        public static final String COLOR = "color";
        public static final int COLOR_1 = 0;
        public static final int COLOR_2 = 1;
        public static final int COLOR_3 = 2;
        public static final int COLOR_4 = 3;
        public static final int COLOR_5 = 4;
        public static final int COLOR_6 = 5;
        public static final int COLOR_7 = 6;
        public static final int COLOR_8 = 7;
        public static final int COLOR_DEFAULT = COLOR_1;

        /**
         * 0: Don't allow data when roaming, 1:Allow data when roaming
         */
        public static final String DATA_ROAMING = "data_roaming";
        public static final int DATA_ROAMING_ENABLE = 1;
        public static final int DATA_ROAMING_DISABLE = 0;
        public static final int DATA_ROAMING_DEFAULT = DATA_ROAMING_DISABLE;


        public static final String SLOT = "slot";
        public static final int SLOT_NONE = -1;

        public static final int ERROR_GENERAL = -1;
        public static final int ERROR_NAME_EXIST = -2;

    }
}
