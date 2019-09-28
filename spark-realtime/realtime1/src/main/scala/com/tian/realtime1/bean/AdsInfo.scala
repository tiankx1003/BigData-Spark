package com.tian.realtime1.bean

import java.sql.Timestamp

/**
 * @author tian
 * @date 2019/9/28 18:34
 * @version 1.0.0
 */
case class AdsInfo(ts: Long,  // 数字型的时间戳
                   timestamp: Timestamp, // 时间戳类型的时间戳
                   dayString: String,  // 2019-09-25
                   hmString: String,  // 10:20
                   area: String,
                   city: String,
                   userId: String,
                   adsId: String)
