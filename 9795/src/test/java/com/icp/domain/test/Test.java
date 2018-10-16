package com.icp.domain.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

public class Test {

	public static void main1(String[] args) throws Exception {
		String source = "/Users/yuzhengqun/Documents/work/9795/whois/whois_result.txt";
		String target = "/Users/yuzhengqun/Documents/work/9795/whois/nameserver_registrar.csv";
		FileReader fr = new FileReader(source);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		JSONObject jsonObject = null;
		Map<String, Integer> nameServer2Registrar = new HashMap<String, Integer>();
		while ((line = br.readLine()) != null) {
			jsonObject = JSONObject.parseObject(line);
			String punycode = jsonObject.getString("punycode");
			String nameServer = jsonObject.getString("nameServer");
			String registrar = jsonObject.getString("registrar");
			String key = nameServer + "_" + registrar;
			if (nameServer2Registrar.containsKey(key)) {
				nameServer2Registrar
						.put(key, nameServer2Registrar.get(key) + 1);
			} else {
				nameServer2Registrar.put(key, 1);
			}
		}
		br.close();
		fr.close();
		FileWriter fw = new FileWriter(target);
		for (String key : nameServer2Registrar.keySet()) {
			String[] array = key.split("_");
			if (array.length > 2) {
				System.out.println(key);
			}
			String nameServer = array[0];
			String registrar = array[1];
			int count = nameServer2Registrar.get(key);
			fw.write(nameServer + "," + registrar.replace(",", "...") + ","
					+ count);
			fw.write("\n");
		}
		fw.close();
	}

	public static void main2(String[] args) throws Exception {
		String source = "/Users/yuzhengqun/Documents/work/9795/whois/whois_result.txt";
		String target = "/Users/yuzhengqun/Documents/work/9795/whois/nameserver_count.csv";
		FileReader fr = new FileReader(source);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		JSONObject jsonObject = null;
		Map<String, Integer> nameServerCount = new HashMap<String, Integer>();
		while ((line = br.readLine()) != null) {
			jsonObject = JSONObject.parseObject(line);
			String punycode = jsonObject.getString("punycode");
			String nameServer = jsonObject.getString("nameServer");
			String registrar = jsonObject.getString("registrar");
			if (nameServer == null || "".equals(nameServer.trim())) {
				continue;
			}
			String key = nameServer.toLowerCase();

			if (nameServerCount.containsKey(key)) {
				nameServerCount.put(key, nameServerCount.get(key) + 1);
			} else {
				nameServerCount.put(key, 1);
			}
		}
		br.close();
		fr.close();
		FileWriter fw = new FileWriter(target);
		for (String key : nameServerCount.keySet()) {
			int count = nameServerCount.get(key);
			fw.write(key + "," + count);
			fw.write("\n");
		}
		fw.close();
	}

	public static void main(String[] args) throws Exception {
		String source = "/Users/yuzhengqun/Documents/work/9795/whois/whois_result.txt";
		String target = "/Users/yuzhengqun/Documents/work/9795/whois/registrar.csv";
		FileReader fr = new FileReader(source);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		JSONObject jsonObject = null;
		Map<String, Integer> nameServerCount = new HashMap<String, Integer>();
		while ((line = br.readLine()) != null) {
			jsonObject = JSONObject.parseObject(line);
			String punycode = jsonObject.getString("punycode");
			String nameServer = jsonObject.getString("nameServer");
			String registrar = jsonObject.getString("registrar");
			String whoisServer = jsonObject.getString("whoisServer");
			String temp = registrar;
			if (!punycode.endsWith(".cn")) {
				if(whoisServer == null){
					continue;
				}
				temp = whoisServer.toLowerCase();
			}
			if(nameServerCount.containsKey(temp)){
				nameServerCount.put(temp, nameServerCount.get(temp)+1);
			}else{
				nameServerCount.put(temp, 1);
			}
		}
		br.close();
		fr.close();
		FileWriter fw = new FileWriter(target);
		for (String key : nameServerCount.keySet()) {
			int count = nameServerCount.get(key);
			fw.write(key + "," + count);
			fw.write("\n");
		}
		fw.close();
	}
}
