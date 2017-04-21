package vn.com.toyota.checkdetail.storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import vn.com.toyota.checkdetail.config.AppConfig;
import vn.com.toyota.checkdetail.model.Setting;
import vn.com.toyota.checkdetail.utils.FileUtils;

public class SettingStorage {
    private static SettingStorage instance;

    public static synchronized SettingStorage getInstance() {
        if (instance == null) {
            instance = new SettingStorage();
        }
        return instance;
    }

    private Setting mSetting;

    private SettingStorage() {
        loadSetting();
    }

    public Setting getSetting() {
        return mSetting;
    }

    public void setSetting(Setting setting) {
        mSetting = setting;
    }

    private void loadSetting() {
        this.mSetting = new Setting(AppConfig.SERVER_IP, AppConfig.SERVER_PORT);
        try {
            String directory = FileUtils.getDirectory();
            String fileName = directory + "/config.txt";

            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(fileName));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split("=");
                    String key = data[0].trim();
                    String value = data[1].trim();
                    if (key.equals("ip")) {
                        this.mSetting.setIp(value);
                    } else if (key.equals("port")) {
                        this.mSetting.setPort(Integer.parseInt(value));
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    br.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}