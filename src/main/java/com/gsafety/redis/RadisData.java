package com.gsafety.redis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.zeone.lifeline.collector.cache.Cache;
import com.zeone.lifeline.collector.cache.CacheHandler;
import com.zeone.lifeline.collector.cache.CacheManager;
import com.zeone.lifeline.collector.util.DateUtil;

/**
 * radis操作
 * 
 * @author ycq
 *
 */
@Component
public class RadisData {

	private CollectorConfiguration CollectorConfiguration;

	public CollectorConfiguration getCollectorConfiguration() {
		return CollectorConfiguration;
	}

	/**
	 * 
	 * @param collectorConfiguration
	 *            redis 配置文件类
	 * @throws Exception
	 *             注入并注册到redis服务器
	 * @Description:
	 */
	@Autowired
	public void setCollectorConfiguration(
			CollectorConfiguration collectorConfiguration) throws Exception {
		CollectorConfiguration = collectorConfiguration;
		CacheManager.initialize(CollectorConfiguration);
		System.err.println("CacheManager.initialize is ok");

	}

	/**
	 * 获取某个设备的最新一条信息
	 * 
	 * @param gateway
	 * @param modular
	 * @param pathn
	 * @return
	 */
	public Map<String, String> getEquipData(final String gateway,
			final String modular, final String pathn) {
		final Map<String, String> data = new HashMap<String, String>();
		try {
			CacheManager.handler(CacheManager.CATEGORY_BRIDGE_REALDATA,	new CacheHandler() {
						public Object handle(Cache cache) throws Exception {
							String cache_key = String.format("BSD-[%s_%s_%s]",gateway, modular, pathn);
							int value_length = 1;
							for (String item : cache.zrange(cache_key,
									value_length)) {
								String[] item_values = item.split("_");
								data.put("time", item_values[0]);
								data.put("value", item_values[1]);
								break;
							}
							return null;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 
	 * @param gateway
	 *            网关号
	 * @param modular
	 *            模块号
	 * @param pathn
	 *            通道号
	 * @return HashMap<String, Integer>
	 * @Description: 返回 一个设备 一秒传输了多少个数据
	 */
	public HashMap<String, Integer> getEquipData1000_Ext(final String gateway,
			final String modular, final String pathn) {
		final HashMap<String, Integer> data = new HashMap<String, Integer>();
		try {
			CacheManager.handler(CacheManager.CATEGORY_BRIDGE_REALDATA,
					new CacheHandler() {
						public Object handle(Cache cache) throws Exception {
							String cache_key = String.format("BSD-[%s_%s_%s]",
									gateway, modular, pathn);
							int value_length = 1000;
							String timeSeq = null;
							Integer showNums = 1;
							String[] item_values = null;
							for (String item : cache.zrange(cache_key,
									value_length)) {
								item_values = item.split("_");
								timeSeq = DateUtil.format(
										new Date(Long.parseLong(item_values[0])),
										"yyyy:MM:dd:HH:mm:ss");
								if (data.containsKey(timeSeq)) {
									showNums = data.get(timeSeq);
									if (null != showNums) {
										data.put(timeSeq, showNums + 1);
									} else {
										data.put(timeSeq, 1);
									}
								} else {
									data.put(timeSeq, 1);
								}
								// break;
							}
							return null;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}
}
