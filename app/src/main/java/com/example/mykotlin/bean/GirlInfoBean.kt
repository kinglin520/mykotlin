package com.example.mykotlin.bean

class GirlInfoBean {

    private var createdAt: String? = null
    private var desc: String? = null
    private var publishedAt: String? = null
    private var source: String? = null
    private var type: String? = null
    private var url: String? = null
    private var used: Boolean = false
    private var who: String? = null
    private var girlHeight: Int = 0
    private var girlWidth: Int = 0

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String) {
        this.createdAt = createdAt
    }

    fun getDesc(): String? {
        return desc
    }

    fun setDesc(desc: String) {
        this.desc = desc
    }

    fun getPublishedAt(): String? {
        return publishedAt
    }

    fun setPublishedAt(publishedAt: String) {
        this.publishedAt = publishedAt
    }

    fun getSource(): String? {
        return source
    }

    fun setSource(source: String) {
        this.source = source
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String) {
        this.url = url
    }

    fun isUsed(): Boolean {
        return used
    }

    fun setUsed(used: Boolean) {
        this.used = used
    }

    fun getWho(): String? {
        return who
    }

    fun setWho(who: String) {
        this.who = who
    }

    fun getGirlHeight(): Int {
        return girlHeight
    }

    fun setGirlHeight(girlHeight: Int) {
        this.girlHeight = girlHeight
    }

    fun getGirlWidth(): Int {
        return girlWidth
    }

    fun setGirlWidth(girlWidth: Int) {
        this.girlWidth = girlWidth
    }
}