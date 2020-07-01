package vn.rin.blog.domain.kit;

import org.apache.shiro.SecurityUtils;
import org.json.JSONObject;
import vn.rin.blog.domain.data.AccountProfile;
import vn.rin.blog.domain.entities.Operation;
import vn.rin.blog.utils.HeaderUtils;

import javax.security.auth.Subject;
import java.util.Date;

/**
 * @author Rin
 */
public class OperationKit {
    // Code constants
    /**
     * Operation code - make report ignored.
     */
    public static final int OPERATION_CODE_C_MAKE_REPORT_IGNORED = 0;

    /**
     * Operation code - make report handled.
     */
    public static final int OPERATION_CODE_C_MAKE_REPORT_HANDLED = 1;

    /**
     * Operation code - update user address.
     */
    public static final int OPERATION_CODE_C_UPDATE_USER_ADDR = 2;

    /**
     * Operation code - remove role.
     */
    public static final int OPERATION_CODE_C_REMOVE_ROLE = 3;

    /**
     * Operation code - change article email push order.
     */
    public static final int OPERATION_CODE_C_CHANGE_ARTICLE_EMAIL_PUSH_ORDER = 4;

    /**
     * Operation code - update breezemoon.
     */
    public static final int OPERATION_CODE_C_UPDATE_BREEZEMOON = 5;

    /**
     * Operation code - remove breezemoon.
     */
    public static final int OPERATION_CODE_C_REMOVE_BREEZEMOON = 6;

    /**
     * Operation code - push telegram.
     */
    public static final int OPERATION_CODE_C_PUSH_TELEGRAM = 7;

    /**
     * Operation code - withdraw B3T.
     */
    public static final int OPERATION_CODE_C_WITHDRAW_B3T = 8;

    /**
     * Operation code - withdraw B3T.
     */
    public static final int OPERATION_CODE_C_REMOVE_UNUSED_TAGS = 9;

    /**
     * Operation code - add role.
     */
    public static final int OPERAIONT_CODE_C_ADD_ROLE = 10;

    /**
     * Operation code - update role permissions.
     */
    public static final int OPERATION_CODE_C_UPDATE_ROLE_PERMS = 11;

    /**
     * Operation code - add ad pos.
     */
    public static final int OPERATION_CODE_C_ADD_AD_POS = 12;

    /**
     * Operation code - update ad pos.
     */
    public static final int OPERATION_CODE_C_UPDATE_AD_POS = 13;

    /**
     * Operation code - add tag.
     */
    public static final int OPERATION_CODE_C_ADD_TAG = 14;

    /**
     * Operation code - stick article.
     */
    public static final int OPERATION_CODE_C_STICK_ARTICLE = 15;

    /**
     * Operation code - cancel stick article.
     */
    public static final int OPERATION_CODE_C_CANCEL_STICK_ARTICLE = 16;

    /**
     * Operation code - generate invitecodes.
     */
    public static final int OPERATION_CODE_C_GENERATE_INVITECODES = 17;

    /**
     * Operation code - update invitecode.
     */
    public static final int OPERATION_CODE_C_UPDATE_INVITECODE = 18;

    /**
     * Operation code - add article.
     */
    public static final int OPERATION_CODE_C_ADD_ARTICLE = 19;

    /**
     * Operation code - add reserved word.
     */
    public static final int OPERATION_CODE_C_ADD_RESERVED_WORD = 20;

    /**
     * Operation code - update reserved word.
     */
    public static final int OPERATION_CODE_C_UPDATE_RESERVED_WORD = 21;

    /**
     * Operation code - remove reserved word.
     */
    public static final int OPERATION_CODE_C_REMOVE_RESERVED_WORD = 22;

    /**
     * Operation code - remove comment.
     */
    public static final int OPERATION_CODE_C_REMOVE_COMMENT = 23;

    /**
     * Operation code - remove article.
     */
    public static final int OPERATION_CODE_C_REMOVE_ARTICLE = 24;

    /**
     * Operation code - add user.
     */
    public static final int OPERATION_CODE_C_ADD_USER = 25;

    /**
     * Operation code - update user.
     */
    public static final int OPERATION_CODE_C_UPDATE_USER = 26;

    /**
     * Operation code - update user email.
     */
    public static final int OPERATION_CODE_C_UPDATE_USER_EMAIL = 27;

    /**
     * Operation code - update user username.
     */
    public static final int OPERATION_CODE_C_UPDATE_USER_NAME = 28;

    /**
     * Operation code - charge point.
     */
    public static final int OPERATION_CODE_C_CHARGE_POINT = 29;

    /**
     * Operation code - deduct point.
     */
    public static final int OPERATION_CODE_C_DEDUCT_POINT = 30;

    /**
     * Operation code - init point.
     */
    public static final int OPERATION_CODE_C_INIT_POINT = 31;

    /**
     * Operation code - exchange point.
     */
    public static final int OPERATION_CODE_C_EXCHANGE_POINT = 32;

    /**
     * Operation code - update article.
     */
    public static final int OPERATION_CODE_C_UPDATE_ARTICLE = 33;

    /**
     * Operation code - update comment.
     */
    public static final int OPERATION_CODE_C_UPDATE_COMMENT = 34;

    /**
     * Operation code - update misc.
     */
    public static final int OPERATION_CODE_C_UPDATE_MISC = 35;

    /**
     * Operation code - update tag.
     */
    public static final int OPERATION_CODE_C_UPDATE_TAG = 36;

    /**
     * Operation code - update domain.
     */
    public static final int OPERATION_CODE_C_UPDATE_DOMAIN = 37;

    /**
     * Operation code - add domain.
     */
    public static final int OPERATION_CODE_C_ADD_DOMAIN = 38;

    /**
     * Operation code - remove domain.
     */
    public static final int OPERATION_CODE_C_REMOVE_DOMAIN = 39;

    /**
     * Operation code - add domain tag.
     */
    public static final int OPERATION_CODE_C_ADD_DOMAIN_TAG = 40;

    /**
     * Operation code - remove domain tag.
     */
    public static final int OPERATION_CODE_C_REMOVE_DOMAIN_TAG = 41;

    /**
     * Operation code - rebuild algolia tag.
     */
    public static final int OPERATION_CODE_C_REBUILD_ALGOLIA_TAG = 42;

    /**
     * Operation code - rebuild algolia user.
     */
    public static final int OPERATION_CODE_C_REBUILD_ALGOLIA_USER = 43;

    /**
     * Operation code - rebuild articles search index.
     */
    public static final int OPERATION_CODE_C_REBUILD_ARTICLES_SEARCH = 44;

    /**
     * Operation code - rebuild article search index.
     */
    public static final int OPERATION_CODE_C_REBUILD_ARTICLE_SEARCH = 45;

    //// Transient ////

    /**
     * Key of operation user name.
     */
    public static final String OPERATION_T_USER_NAME = "operationUserName";

    /**
     * Key of operation content.
     */
    public static final String OPERATION_T_CONTENT = "operationContent";

    /**
     * Key of operation time.
     */
    public static final String OPERATION_T_TIME = "operationTime";

    /**
     * Creates an operation with the specified request and code,
     *
     * @param code   the specified code
     * @param dataId the specified data id
     * @return an operation
     */
    public static Operation newOperation(final int code, final String dataId) {
        final String ip = HeaderUtils.getRemoteAddr();
        final String ua = HeaderUtils.getUserAgent();

        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        AccountProfile user = (AccountProfile) subject.getPrincipal();

        Operation operation = new Operation();
        operation.setUserId(user.getId());
        operation.setCreated(new Date());
        operation.setCode(code);
        operation.setDataId(dataId);
        operation.setIp(ip);
        operation.setUa(ua);

        return operation;
    }
}
