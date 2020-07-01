/**
 *
 */
package vn.rin.blog.domain.template.directive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.data.PostVO;
import vn.rin.blog.domain.entity.ChannelEntity;
import vn.rin.blog.domain.template.DirectiveHandler;
import vn.rin.blog.domain.template.TemplateDirective;
import vn.rin.blog.service.ChannelService;
import vn.rin.blog.service.PostService;
import vn.rin.blog.utils.BeanMapUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Rin
 */
@Component
public class ContentsDirective extends TemplateDirective {
    @Autowired
    private PostService postService;
    @Autowired
    private ChannelService channelService;

    @Override
    public String getName() {
        return "contents";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer channelId = handler.getInteger("channelId", 0);
        String order = handler.getString("order", Constants.order.NEWEST);

        Set<Integer> excludeChannelIds = new HashSet<>();

        if (channelId <= 0) {
            List<ChannelEntity> channels = channelService.findAll(Constants.STATUS_CLOSED);
            if (channels != null) {
                channels.forEach((c) -> excludeChannelIds.add(c.getId()));
            }
        }

        Pageable pageable = wrapPageable(handler, Sort.by(Sort.Direction.DESC, BeanMapUtils.postOrder(order)));
        Page<PostVO> result = postService.paging(pageable, channelId, excludeChannelIds);
        handler.put(RESULTS, result).render();
    }
}
