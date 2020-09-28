<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${daoClassName}" >

    <resultMap type="${entityClassName}" id="resultMap">
        <#list attrList as attr>
            <result property="${attr.name}" column="${attr.value}"></result>
        </#list>
    </resultMap>

    <sql id="Base_Column_List" >
        <#list attrList as attr>
            ${attr.value} <#if attr_has_next>,</#if>
        </#list>
    </sql>

    <insert id="insertEntity" parameterType="${entityClassName}" >

        insert into
        ${tableName}
        (
        <#list attrList as attr>
            ${attr.value} <#if attr_has_next>,</#if>
        </#list>
        )
        values
        (
        <#list attrList as attr>
            <#noparse>#{</#noparse>${attr.name}<#noparse>}</#noparse><#if attr_has_next>,</#if>
        </#list>
        )
    </insert>

    <select id="selectWithId" resultMap="resultMap" parameterType="java.lang.Integer" >
        select
        <include refid="Base_Column_List"/>
        from ${tableName}
        where id = <#noparse>#{</#noparse>id,jdbcType=INTEGER<#noparse>}</#noparse>
    </select>

    <update id="updateWithId" parameterType="${entityClassName}" >
        update ${tableName}
        set
        <#list attrList as attr>
            <#if attr_index != 0>
            ${attr.value} = <#noparse>#{</#noparse>${attr.name}<#noparse>}</#noparse><#if attr_has_next>,</#if>
            </#if>
        </#list>
        where id = <#noparse>#{</#noparse>id,jdbcType=INTEGER<#noparse>}</#noparse>
    </update>

    <delete id="deleteWithId" parameterType="java.lang.Integer" >
        delete from ${tableName}
        where id = <#noparse>#{</#noparse>id,jdbcType=INTEGER<#noparse>}</#noparse>
    </delete>

    <select id="selectByCondition" parameterType="java.lang.String" resultMap="resultMap">
        select
        <include refid="Base_Column_List"/>
        from ${tableName}
        where  1=1
        <#list attrList as attr>
            <#if attr_index != 0>
                <if test="${attr.name} != null ">
                    AND ${attr.value} = <#noparse>#{</#noparse>${attr.name}<#noparse>}</#noparse>
                </if>
            </#if>
        </#list>
    </select>

</mapper>
