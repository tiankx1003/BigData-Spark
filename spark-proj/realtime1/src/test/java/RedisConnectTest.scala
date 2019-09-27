import com.tian.realtime.util.RedisUtil

/**
 * @author tian
 * @date 2019/9/27 19:29
 * @version 1.0.0
 */
object RedisConnectTest {
    def main(args: Array[String]): Unit = {
        val client = RedisUtil.getJedisClient
        client.smembers(s"blacklist:test")
    }
}
