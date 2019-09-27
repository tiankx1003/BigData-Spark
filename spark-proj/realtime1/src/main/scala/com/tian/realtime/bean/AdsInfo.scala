package com.tian.realtime.bean

import java.sql.Timestamp

/**
 * @author tian
 * @date 2019/9/27 9:00
 * @version 1.0.0
 * @param ts        数字型时间戳
 * @param timestamp 时间戳类型的时间戳
 * @param dayString 2019-09-25
 * @param hmString  12:00
 * @param area      地区
 * @param city      城市
 * @param userId    用户ID
 * @param adsId     广告ID
 */
case class AdsInfo(ts: Long,
                   timestamp: Timestamp,
                   dayString: String,
                   hmString: String,
                   area: String,
                   city: String,
                   userId: String,
                   adsId: String)
