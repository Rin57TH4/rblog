package vn.rin.blog.service.impl;

import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import vn.rin.blog.domain.data.vo.OptionVO;
import vn.rin.blog.domain.entities.Option;
import vn.rin.blog.domain.kit.OptionKit;
import vn.rin.blog.repositories.OptionRepo;
import vn.rin.blog.service.OptionService;
import vn.rin.blog.utils.BeanUtil;

import java.util.List;

/**
 * @author Rin
 */
@Service
@AllArgsConstructor
public class OptionServiceImpl implements OptionService {
    private final OptionRepo optionRepo;

    @Override
    public void removeOption(String id) {
        optionRepo.deleteById(id);
    }

    @Override
    public void addOption(Option option) {
        option.setId(IdUtil.fastSimpleUUID());
        optionRepo.save(option);
    }

    @Override
    public void updateOption(Option option) {
        optionRepo.save(option);
    }


//    public int getOnlineMemberCount() {
//        int ret = 0;
//        for (final Set<WebSocketSession> value : UserChannel.SESSIONS.values()) {
//            ret += value.size();
//        }
//
//        return ret;
//    }

    /**
     * Gets the online visitor count.
     *
     * @return online visitor count
     */
//    public int getOnlineVisitorCount() {
//        final int ret = ArticleChannel.SESSIONS.size() + ArticleListChannel.SESSIONS.size() + ChatroomChannel.SESSIONS.size() + getOnlineMemberCount();
//
//        try {
//            final JSONObject maxOnlineMemberCntRecord = optionRepository.get(Option.ID_C_STATISTIC_MAX_ONLINE_VISITOR_COUNT);
//            final int maxOnlineVisitorCnt = maxOnlineMemberCntRecord.optInt(Option.OPTION_VALUE);
//
//            if (maxOnlineVisitorCnt < ret) {
//                // Updates the max online visitor count
//
//                final Transaction transaction = optionRepository.beginTransaction();
//
//                try {
//                    maxOnlineMemberCntRecord.put(Option.OPTION_VALUE, String.valueOf(ret));
//                    optionRepository.update(maxOnlineMemberCntRecord.optString(Keys.OBJECT_ID), maxOnlineMemberCntRecord);
//
//                    transaction.commit();
//                } catch (final RepositoryException e) {
//                    if (transaction.isActive()) {
//                        transaction.rollback();
//                    }
//
//                    LOGGER.log(Level.ERROR, "Updates the max online visitor count failed", e);
//                }
//            }
//        } catch (final RepositoryException ex) {
//            LOGGER.log(Level.ERROR, "Gets online visitor count failed", ex);
//        }
//
//        return ret;
//    }

    /**
     * Gets the statistic.
     *
     * @return statistic
     */
    public List<Option> getStatistic() {
        return optionRepo.findByCategory(OptionKit.CATEGORY_C_STATISTIC);
    }

    /**
     * Checks whether the specified content contains reserved words.
     *
     * @param content the specified content
     * @return {@code true} if it contains reserved words, returns {@code false} otherwise
     */
    public boolean containReservedWord(final String content) {
        if (StringUtils.isBlank(content)) {
            return false;
        }

        try {
            final List<Option> reservedWords = getReservedWords();

            for (final Option reservedWord : reservedWords) {
                if (content.contains(reservedWord.getValue())) {
                    return true;
                }
            }

            return false;
        } catch (final Exception e) {
            return true;
        }
    }

    /**
     * Gets the reserved words.
     *
     * @return reserved words
     */
    public List<Option> getReservedWords() {
        return optionRepo.findByCategory(OptionKit.CATEGORY_C_RESERVED_WORDS);
    }

    /**
     * Checks whether the specified word is a reserved word.
     *
     * @param word the specified word
     * @return {@code true} if it is a reserved word, returns {@code false} otherwise
     */
    public boolean isReservedWord(final String word) {
        return optionRepo.countByCategoryAndValue(word, OptionKit.CATEGORY_C_RESERVED_WORDS) > 0;
    }

    /**
     * Gets allow register option value.
     *
     * @return allow register option value, return {@code null} if not found
     */
    public String getAllowRegister() {
        Option option = optionRepo.findById(OptionKit.ID_C_MISC_ALLOW_REGISTER).orElse(null);
        if (option == null)
            return null;
        else {
            return option.getValue();
        }
    }

    /**
     * Gets the miscellaneous.
     *
     * @return misc
     */
    public List<OptionVO> getMisc() {
        final List<Option> ret = optionRepo.findByCategory(OptionKit.CATEGORY_C_MISC);
        List<OptionVO> voList = BeanUtil.copyProperties(ret, OptionVO.class);
        for (final OptionVO option : voList) {
            option.setLabel(option.getId() + " label lang");
        }
        return voList;
    }

    /**
     * Gets an option by the specified id.
     *
     * @param optionId the specified id
     * @return option, return {@code null} if not found
     */
    public Option getOption(final String optionId) {
        return optionRepo.findById(optionId).orElse(null);
    }
}
