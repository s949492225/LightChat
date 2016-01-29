package cn.syiyi.com.lightchat.bean;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * LightChat...........
 * cn.syiyi.com.lightchat.bean...........
 * Created by lintao.song on 2016/1/27.
 */
public class ContactInfo {
    public static final int TYPE_SESSION = 0;
    public static final int TYPE_CONTACT = 1;


    private List<String> titles;
    private List<Contact> contacts;
    private List<Object> items;
    private int count;

    public List<Object> getItems() {
        return items;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        List<String> titles = new ArrayList<>();

        for (int i = 0; i < contacts.size(); i++) {
            // 得到字母
            String name = getAlpha(contacts.get(i).getSortKey());
            if (!titles.contains(name)) {
                titles.add(name);
            }
        }
        Collections.sort(titles, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                if (lhs.equals("#") || rhs.equals("#")) {
                    return lhs.equals("#") ? 1 : -1;
                } else {
                    return lhs.compareTo(rhs);
                }
            }
        }); // 根据首字母进行排序
        Collections.sort(this.contacts);
        setTitles(titles);

        items = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i);
            items.add(title);
            for (int j=index;j < this.contacts.size(); j++) {
                Contact contact = contacts.get(j);
                if (title.toUpperCase().equals(contact.sortKey.trim().toUpperCase())) {
                    items.add(contact);
                } else {
                    index=j;
                    break;
                }
            }
        }

    }

    public int getCount() {
        return count;
    }

    private void setCount(int count) {
        this.count = count;
    }


    private void setTitles(List<String> titles) {
        this.titles = titles;
        setCount(titles.size() + contacts.size());
    }

    public int getType(int position) {
        Object obj = getItem(position);
        if (obj instanceof String) {
            return TYPE_SESSION;
        } else {
            return TYPE_CONTACT;
        }
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public static class Contact implements Comparable<Contact> {
        private int contactId; //id
        private String desplayName;//姓名
        private String phoneNum; // 电话号码
        private String sortKey; // 排序用的
        private Long photoId; // 图片id

        public int getContactId() {
            return contactId;
        }

        public void setContactId(int contactId) {
            this.contactId = contactId;
        }

        public String getDesplayName() {
            return desplayName;
        }

        public void setDesplayName(String desplayName) {
            this.desplayName = desplayName;
            setSortKey(getAlpha(desplayName.trim()));
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public Long getPhotoId() {
            return photoId;
        }

        public void setPhotoId(Long photoId) {
            this.photoId = photoId;
        }

        public String getSortKey() {
            return sortKey;
        }

        private void setSortKey(String sortKey) {
            this.sortKey = sortKey;
        }


        @Override
        public int compareTo(Contact another) {
            if (this.sortKey.equals("#") || another.sortKey.equals("#")) {
                return this.sortKey.equals("#") ? 1 : -1;
            } else {
                return this.sortKey.compareTo(another.sortKey);
            }
        }
    }

    /**
     * 获取首字母
     *
     * @param str
     * @return
     */
    public static String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        String c = str.trim().substring(0, 1);
        if (Pinyin.isChinese(c.charAt(0))) {
            c = Pinyin.toPinyin(c.charAt(0)).substring(0, 1);
        }
        // 正则表达式匹配
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c).matches()) {
            return (c).toUpperCase(); // 将小写字母转换为大写
        } else {
            return "#";
        }
    }
}
