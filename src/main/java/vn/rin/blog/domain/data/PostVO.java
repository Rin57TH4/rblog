package vn.rin.blog.domain.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.entity.ChannelEntity;
import vn.rin.blog.domain.entity.PostAttributeEntity;
import vn.rin.blog.domain.entity.PostEntity;

import java.io.Serializable;

/**
 * @author Rin
 */
@Getter
@Setter
public class PostVO extends PostEntity implements Serializable {
    private static final long serialVersionUID = -1144627551517707139L;

    private String editor;
    private String content;
    private UserVO author;
    private ChannelEntity channel;

    @JSONField(serialize = false)
    private PostAttributeEntity attribute;

    public String[] getTagsArray() {
        if (StringUtils.isNotBlank(super.getTags())) {
            return super.getTags().split(Constants.SEPARATOR);
        }
        return null;
    }

}
