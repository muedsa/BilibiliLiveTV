package com.muedsa.bilibililiveapiclient;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;

public class BilibiliApiContainer {
    public static final String COOKIE_KEY_BUVID3 = "buvid3";
    public static final String COOKIE_KEY_BUVID4 = "buvid4";

    public static final String COOKIE_KEY_USER_ID = "DedeUserID";

    public static final String COOKIE_KEY_USER_ID_HSAH = "DedeUserID__ckMd5";

    public static final String COOKIE_KEY_SESSDATA = "SESSDATA";

    public static final String COOKIE_KEY_BILI_JCT = "bili_jct";


    public static final String QUERY_KEY_MID = "mid";
    public static final String QUERY_KEY_PN = "pn";
    public static final String QUERY_KEY_PS = "ps";
    public static final String QUERY_KEY_INDEX = "index";
    public static final String QUERY_KEY_ORDER = "order";
    public static final String QUERY_KEY_ORDER_AVOIDED = "order_avoided";
    public static final String QUERY_KEY_PLATFORM = "platform";
    public static final String QUERY_KEY_WEB_LOCATION = "web_location";

    public static final String QUERY_KEY_DM_IMG_STR = "dm_img_str";

    public static final String QUERY_KEY_DM_IMG_LIST = "dm_img_list";

    public static final String QUERY_KEY_DM_COVER_IMG_STR = "dm_cover_img_str";

    public static final String ORDER_BY_PUBLIC_DATE = "pubdate";

    public static final String WEB_LOCATION_SPACE = "1550101";

    public static final String PLATFORM_WEB = "platform";

    public static final String DYNAMIC_MAJOR_TYPE_ARCHIVE = "MAJOR_TYPE_ARCHIVE";

    public static final String DYNAMIC_TYPE_AV = "DYNAMIC_TYPE_AV";

    public static final String DISABLED_WEB_GL = "bm8gd2ViZ2";

    public static final String DM_IMG_STR = "V2ViR0wgMS4wIChPcGVuR0wgRVMgMi4wIENocm9taXVtKQ";

    public static final String DM_COVER_IMG_STR = "QU5HTEUgKEludGVsLCBJbnRlbChSKSBVSEQgR3JhcGhpY3MgNjMwICgweDAwMDA5QkM4KSBEaXJlY3QzRDExIHZzXzVfMCBwc181XzAsIEQzRDExKUdvb2dsZSBJbmMuIChJbnRlbC";

    public static final JSONArray DM_COVER_IMG_LIST = JSON.parseArray("[{\"x\":4254,\"y\":2452,\"z\":0,\"timestamp\":35723,\"type\":0},{\"x\":3899,\"y\":2005,\"z\":59,\"timestamp\":35824,\"type\":0},{\"x\":3598,\"y\":1582,\"z\":124,\"timestamp\":35924,\"type\":0},{\"x\":3659,\"y\":1641,\"z\":283,\"timestamp\":36024,\"type\":0},{\"x\":3204,\"y\":1161,\"z\":133,\"timestamp\":36125,\"type\":0},{\"x\":2697,\"y\":517,\"z\":37,\"timestamp\":36226,\"type\":0},{\"x\":2626,\"y\":379,\"z\":121,\"timestamp\":36328,\"type\":0},{\"x\":3120,\"y\":872,\"z\":618,\"timestamp\":36444,\"type\":0},{\"x\":3177,\"y\":929,\"z\":675,\"timestamp\":36755,\"type\":1},{\"x\":3411,\"y\":1243,\"z\":830,\"timestamp\":36856,\"type\":0},{\"x\":3227,\"y\":1460,\"z\":317,\"timestamp\":37063,\"type\":0},{\"x\":3112,\"y\":1377,\"z\":175,\"timestamp\":37166,\"type\":0},{\"x\":3218,\"y\":1488,\"z\":266,\"timestamp\":37377,\"type\":0},{\"x\":4944,\"y\":3405,\"z\":154,\"timestamp\":37477,\"type\":0},{\"x\":6180,\"y\":4270,\"z\":985,\"timestamp\":37577,\"type\":0},{\"x\":6877,\"y\":5146,\"z\":1168,\"timestamp\":37679,\"type\":0},{\"x\":6413,\"y\":5147,\"z\":758,\"timestamp\":37780,\"type\":0},{\"x\":5563,\"y\":4489,\"z\":160,\"timestamp\":37881,\"type\":0},{\"x\":7167,\"y\":6172,\"z\":1757,\"timestamp\":37982,\"type\":0},{\"x\":7243,\"y\":6298,\"z\":1844,\"timestamp\":38082,\"type\":0},{\"x\":6418,\"y\":5487,\"z\":1023,\"timestamp\":38218,\"type\":0},{\"x\":5884,\"y\":4953,\"z\":489,\"timestamp\":38561,\"type\":1},{\"x\":6155,\"y\":5223,\"z\":763,\"timestamp\":39065,\"type\":0},{\"x\":7321,\"y\":6357,\"z\":1979,\"timestamp\":39166,\"type\":0},{\"x\":7035,\"y\":6075,\"z\":1750,\"timestamp\":39267,\"type\":0},{\"x\":7586,\"y\":6625,\"z\":2304,\"timestamp\":39379,\"type\":0},{\"x\":7872,\"y\":6911,\"z\":2590,\"timestamp\":39484,\"type\":1},{\"x\":7022,\"y\":6060,\"z\":1743,\"timestamp\":39729,\"type\":0},{\"x\":7588,\"y\":5682,\"z\":2703,\"timestamp\":39830,\"type\":0},{\"x\":4782,\"y\":2692,\"z\":12,\"timestamp\":39940,\"type\":0},{\"x\":5515,\"y\":3383,\"z\":733,\"timestamp\":40042,\"type\":0},{\"x\":6803,\"y\":4628,\"z\":1989,\"timestamp\":40143,\"type\":0},{\"x\":7080,\"y\":4864,\"z\":2251,\"timestamp\":40808,\"type\":0},{\"x\":5415,\"y\":1830,\"z\":507,\"timestamp\":40909,\"type\":0},{\"x\":7680,\"y\":3282,\"z\":3026,\"timestamp\":41011,\"type\":0},{\"x\":5892,\"y\":870,\"z\":1270,\"timestamp\":41112,\"type\":0},{\"x\":4896,\"y\":-639,\"z\":249,\"timestamp\":41212,\"type\":0},{\"x\":5888,\"y\":325,\"z\":1256,\"timestamp\":41317,\"type\":0},{\"x\":4797,\"y\":-842,\"z\":324,\"timestamp\":42033,\"type\":0},{\"x\":5210,\"y\":187,\"z\":982,\"timestamp\":42138,\"type\":0},{\"x\":4095,\"y\":3043,\"z\":2536,\"timestamp\":43488,\"type\":0},{\"x\":2235,\"y\":628,\"z\":961,\"timestamp\":43588,\"type\":0},{\"x\":4373,\"y\":2766,\"z\":3099,\"timestamp\":43689,\"type\":1},{\"x\":4879,\"y\":1495,\"z\":2059,\"timestamp\":43789,\"type\":0},{\"x\":5706,\"y\":637,\"z\":2053,\"timestamp\":43891,\"type\":0},{\"x\":4802,\"y\":-797,\"z\":577,\"timestamp\":43992,\"type\":0},{\"x\":5582,\"y\":28,\"z\":946,\"timestamp\":44092,\"type\":0},{\"x\":5911,\"y\":496,\"z\":1479,\"timestamp\":44192,\"type\":0},{\"x\":7478,\"y\":2171,\"z\":3090,\"timestamp\":44293,\"type\":0},{\"x\":9649,\"y\":4368,\"z\":5275,\"timestamp\":44401,\"type\":0}]");
}
