var Public = Public || {};
var Business = Business || {};
Public.isIE6 = !window.XMLHttpRequest; // ie6

/* ��ȡURL����ֵ */
Public.getRequest = Public.urlParam = function () {
    var param, url = location.search, theRequest = {};
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0, len = strs.length; i < len; i++) {
            param = strs[i].split("=");
            theRequest[param[0]] = decodeURIComponent(param[1]);
        }
    }
    return theRequest;
};

/*
 * ͨ��post���󣬷���json url:�����ַ�� params�����ݵĲ���{...}�� callback������ɹ��ص�
 */
Public.ajaxPost = function (url, params, callback) {
    $.ajax({
        type: "POST",
        url: url,
        contentType: 'application/json; charset=utf-8',
        data: params,
        dataType: "json",
        async: false,
        success: function (data, status) {
            callback(data);
        },
        error: function (err) {
            /**
             parent.Public.tips({
				type : 1,
				content : '����ʧ�ܣ����������������ӣ�'
			});
             **/
            $.messager.alert('����', "����ʧ�ܣ����������������ӣ�", 'error');
        }
    });
};


Public.ajaxGet = function (url, params, callback) {
    $.ajax({
        type: "GET",
        url: url,
        data: params,
        dataType: "json",
        async: false,
        success: function (data, status) {
            callback(data);
        },
        error: function (err) {
            /**
             parent.Public.tips({
				type : 1,
				content : '����ʧ�ܣ����������������ӣ�'
			});
             **/
            $.messager.alert('����', "����ʧ�ܣ����������������ӣ�", 'error');
        }
    });
};


/* ������ʾ */
Public.tips = function (options) {
    return new Public.Tips(options);
}
Public.Tips = function (options) {
    var defaults = {
        renderTo: 'body',
        type: 0,
        autoClose: true,
        removeOthers: true,
        time: undefined,
        top: 10,
        onClose: null,
        onShow: null
    }
    this.options = $.extend({}, defaults, options);
    this._init();

    !Public.Tips._collection ? Public.Tips._collection = [this]
        : Public.Tips._collection.push(this);

}

Public.Tips.removeAll = function () {
    try {
        for (var i = Public.Tips._collection.length - 1; i >= 0; i--) {
            Public.Tips._collection[i].remove();
        }
    } catch (e) {
    }
}

Public.Tips.prototype = {
    _init: function () {
        var self = this, opts = this.options, time;
        if (opts.removeOthers) {
            Public.Tips.removeAll();
        }

        this._create();

        this.closeBtn.bind('click', function () {
            self.remove();
        });

        if (opts.autoClose) {
            time = opts.time || opts.type == 1 ? 5000 : 3000;
            window.setTimeout(function () {
                self.remove();
            }, time);
        }

    },

    _create: function () {
        var opts = this.options;
        this.obj = $(
            '<div class="ui-tips"><i></i><span class="close"></span></div>')
            .append(opts.content);
        this.closeBtn = this.obj.find('.close');

        switch (opts.type) {
            case 0:
                this.obj.addClass('ui-tips-success');
                break;
            case 1:
                this.obj.addClass('ui-tips-error');
                break;
            case 2:
                this.obj.addClass('ui-tips-warning');
                break;
            default:
                this.obj.addClass('ui-tips-success');
                break;
        }

        this.obj.appendTo('body').hide();
        this._setPos();
        if (opts.onShow) {
            opts.onShow();
        }

    },

    _setPos: function () {
        var self = this, opts = this.options;
        if (opts.width) {
            this.obj.css('width', opts.width);
        }
        var h = this.obj.outerHeight(), winH = $(window).height(), scrollTop = $(
            window).scrollTop();
        // var top = parseInt(opts.top) ? (parseInt(opts.top) + scrollTop) :
        // (winH > h ? scrollTop+(winH - h)/2 : scrollTop);
        var top = parseInt(opts.top) + scrollTop;
        this.obj.css({
            position: Public.isIE6 ? 'absolute' : 'fixed',
            left: '50%',
            top: top,
            zIndex: '9999',
            marginLeft: -self.obj.outerWidth() / 2
        });

        window.setTimeout(function () {
            self.obj.show().css({
                marginLeft: -self.obj.outerWidth() / 2
            });
        }, 150);

        if (Public.isIE6) {
            $(window).bind('resize scroll', function () {
                var top = $(window).scrollTop() + parseInt(opts.top);
                self.obj.css('top', top);
            })
        }
    },

    remove: function () {
        var opts = this.options;
        this.obj.fadeOut(200, function () {
            $(this).remove();
            if (opts.onClose) {
                opts.onClose();
            }
        });
    }
};
// ��ֵ��ʾ��ʽת��
Public.numToCurrency = function (val, dec) {
    val = parseFloat(val);
    dec = dec || 2; // С��λ
    if (val === 0 || isNaN(val)) {
        return '';
    }
    val = val.toFixed(dec).split('.');
    var reg = /(\d{1,3})(?=(\d{3})+(?:$|\D))/g;
    return val[0].replace(reg, "$1,") + '.' + val[1];
};
// ��ֵ��ʾ
Public.currencyToNum = function (val) {
    var val = String(val);
    if ($.trim(val) == '') {
        return 0;
    }
    val = val.replace(/,/g, '');
    val = parseFloat(val);
    return isNaN(val) ? 0 : val;
};
// ֻ������������
Public.numerical = function (e) {
    var allowed = '0123456789.-', allowedReg;
    allowed = allowed.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, '\\$&');
    allowedReg = new RegExp('[' + allowed + ']');
    var charCode = typeof e.charCode != 'undefined' ? e.charCode : e.keyCode;
    var keyChar = String.fromCharCode(charCode);
    if (!e.ctrlKey && charCode != 0 && !allowedReg.test(keyChar)) {
        e.preventDefault();
    }
    ;
};

// ����ֻ������������ַ�����֧�����ĵĿ���
Public.limitInput = function (obj, allowedReg) {
    var ctrlKey = null;
    obj.css('ime-mode', 'disabled').on('keydown', function (e) {
        ctrlKey = e.ctrlKey;
    }).on(
        'keypress',
        function (e) {
            allowedReg = typeof allowedReg == 'string' ? new RegExp(
                allowedReg) : allowedReg;
            var charCode = typeof e.charCode != 'undefined' ? e.charCode
                : e.keyCode;
            var keyChar = $.trim(String.fromCharCode(charCode));
            if (!ctrlKey && charCode != 0 && charCode != 13
                && !allowedReg.test(keyChar)) {
                e.preventDefault();
            }
        });
};
// ����������ַ�����
Public.limitLength = function (obj, count) {
    obj.on('keyup', function (e) {
        if (count < obj.val().length) {
            e.preventDefault();
            obj.val(obj.val().substr(0, count));
        }
    });
};
/* ������ҳǩ�� */
Public.pageTab = function () {
    $(document)
        .on(
            'click',
            '[rel=pageTab]',
            function (e) {
                e.preventDefault();
                // var right = $(this).data('right');
                // if (right && !Business.verifyRight(right)) {
                // return false;
                // };
                var tabid = $(this).attr('tabid'), url = $(this).attr(
                    'href'), showClose = $(this).attr('showClose'), text = $(
                    this).attr('tabTxt')
                    || $(this).text(), parentOpen = $(this).attr(
                    'parentOpen');
                if (parentOpen) {
                    parent.tab.addTabItem({
                        tabid: tabid,
                        text: text,
                        url: url,
                        showClose: showClose
                    });
                } else {
                    tab.addTabItem({
                        tabid: tabid,
                        text: text,
                        url: url,
                        showClose: showClose
                    });
                }
            });
};

$.fn.artTab = function (options) {
    var defaults = {};
    var opts = $.extend({}, defaults, options);
    var callback = opts.callback || function () {
    };
    this.each(function () {
        var $tab_a = $("dt>a", this);
        var $this = $(this);
        $tab_a.bind("click", function () {
            var target = $(this);
            target.siblings().removeClass("cur").end().addClass("cur");
            var index = $tab_a.index(this);
            var showContent = $("dd>div", $this).eq(index);
            showContent.siblings().hide().end().show();
            callback(target, showContent, opts);
        });
        if (opts.tab)
            $tab_a.eq(opts.tab).trigger("click");
        if (location.hash) {
            var tabs = location.hash.substr(1);
            $tab_a.eq(tabs).trigger("click");
        }
    });
};

$.fn.enterKey = function () {
    this.each(function () {
        $(this).keydown(function (e) {
            if (e.which == 13) {
                var ref = $(this).data("ref");
                if (ref) {
                    $('#' + ref).select().focus().click();
                } else {
                    eval($(this).data("enterKeyHandler"));
                }
            }
        });
    });
};

// inputռλ��
$.fn.placeholder = function () {
    this.each(function () {
        $(this).focus(function () {
            if ($.trim(this.value) == this.defaultValue) {
                this.value = '';
            }
            $(this).removeClass('ui-input-ph');
        }).blur(function () {
            var val = $.trim(this.value);
            if (val == '' || val == this.defaultValue) {
                $(this).addClass('ui-input-ph');
            }
            val == '' && $(this).val(this.defaultValue);
        });
    });
};

// ��ѡ����
$.fn.cssRadio = function (opts) {
    var opts = $.extend({}, opts);
    var $_radio = $('label.radio', this), $_this = this;

    var radioclick = function (event) {
        $_radio.find("input").removeAttr("checked");
        $_radio.removeClass("checked");
        $(this).find("input").attr("checked", "checked");
        $(this).addClass("checked");

        typeof opts.callback == 'function' && opts.callback($(this));
    }

    $_radio.each(function () {
        var self = $(this);
        if (self.find("input")[0].checked) {
            self.addClass("checked");
        }
        ;

    }).hover(function () {
        $(this).addClass("over");
    }, function () {
        $(this).removeClass("over");
    }).click(radioclick);

    return {
        getValue: function () {
            return $_radio.find("input[checked]").val();
        },
        setValue: function (index) {
            return $_radio.eq(index).click();
        },
        disable: function () {
            $_radio.each(function () {
                var self = $(this);
                if (self.find("input")[0].checked) {
                    self.removeClass('checked').addClass("dis_check");
                }
                ;
            }).unbind('click');
        },
        enable: function () {
            $_radio.each(function () {
                var self = $(this);
                if (self.find("input")[0].checked) {
                    self.removeClass('dis_check').addClass("checked");
                }
                ;
            }).bind('click', radioclick);
        }
    }
};
// ��ѡ����
$.fn.cssCheckbox = function () {
    var $_chk = $(".chk", this);
    $_chk.each(function () {
        if ($(this).find("input")[0].checked) {
            $(this).addClass("checked");
        }
        ;
        if ($(this).find("input")[0].disabled) {
            $(this).addClass("dis_check");
        }
        ;
    }).hover(function () {
        $(this).addClass("over")
    }, function () {
        $(this).removeClass("over")
    }).click(function (event) {
        if ($(this).find("input")[0].disabled) {
            return;
        }
        ;
        $(this).toggleClass("checked");
        $(this).find("input")[0].checked = !$(this).find("input")[0].checked;
        event.preventDefault();
    });

    return {
        chkAll: function () {
            $_chk.addClass("checked");
            $_chk.find("input").attr("checked", "checked");
        },
        chkNot: function () {
            $_chk.removeClass("checked");
            $_chk.find("input").removeAttr("checked");
        },
        chkVal: function () {
            var val = [];
            $_chk.find("input:checked").each(function () {
                val.push($(this).val());
            });
            return val;
        }
    }
};

Public.getDefaultPage = function () {
    var win = window.self;
    do {
        if (win.CONFIG) {
            return win;
        }
        win = win.parent;
    } while (true);
};

// Ȩ����֤
Business.verifyRight = function (right) {
    var system = Public.getDefaultPage().SYSTEM;
    var isAdmin = system.isAdmin;
    var siExperied = system.siExpired;
    var rights = system.rights;
    if (isAdmin && !siExperied) {
        return true;
    }
    ;

    if (siExperied) {
        if (rights[right]) {
            return true;
        } else {
            var html = [
                '<div class="ui-dialog-tips">',
                '<h4 class="tit">лл��ʹ�ñ���Ʒ�����ĵ�ǰ�����Ѿ����ڣ�����3���º����ݽ����Զ�������������ʹ���빺��/���ѣ�</h4>',
                '</div>'].join('');
            $.dialog({
                width: 280,
                title: 'ϵͳ��ʾ',
                icon: 'alert.gif',
                fixed: true,
                lock: true,
                resize: false,
                ok: true,
                content: html
            });
            return false;
        }
    } else {
        if (rights[right]) {
            return true;
        } else {
            var html = ['<div class="ui-dialog-tips">',
                '<h4 class="tit">��û�иù��ܵ�ʹ��Ȩ��Ŷ��</h4>',
                '<p>����ϵ����Ա��' + system.realName + '��Ϊ����Ȩ��</p>', '</div>']
                .join('');
            $.dialog({
                width: 240,
                title: 'ϵͳ��ʾ',
                icon: 'alert.gif',
                fixed: true,
                lock: true,
                resize: false,
                ok: true,
                content: html
            });
            return false;
        }
    }
    ;
};

Business.customerCombo = function ($_obj, opts, $_vobj, append) {
    if ($_obj.length == 0) {
        return;
    }
    ;

    var opts = $.extend(true, {
        data: '/Customer/CustomerAutoData',
        ajaxOptions: {
            formatData: function (data) {
                return data.rows;
            }
        },
        width: 200,
        height: 300,
        formatText: function (row) {

            return row.Name;
        },
        text: 'Name',
        value: 'CustomerId',
        defaultSelected: 0,
        defaultFlag: false,
        cache: false,
        editable: true,
        loadOnce: false,
        callback: {
            onChange: function (data) {
                if (data) {
                    $_vobj.val(data.CustomerId);
                    //
                    if (append)
                        $.isFunction(append.onChange) && append.onChange(data);
                }
            }
        }
    }, opts);

    if (append && append.showAdd)
        opts.extraListHtml = '<a href="javascript:void(0);" id="quickAddCustomer" class="quick-add-link"><i class="ui-icon-add"></i>�����ͻ�</a>';

    var customerCombo = $_obj.combo(opts).getCombo();
    // �����ͻ�
    $('#quickAddCustomer').on(
        'click',
        function (e) {
            e.preventDefault();

            $.dialog({
                title: '�����ͻ�',
                content: 'url:/Customer/CustomerEdit',
                data: {
                    oper: 'add',
                    callback: function (data, oper, dialogWin) {

                        customerCombo.loadData(
                            '/Customer/CustomerAutoData', [
                                'CustomerId', data.CustomerId]);
                        dialogWin && dialogWin.api.close();
                    }
                },
                width: 640,
                height: 456,
                max: false,
                min: false,
                cache: false,
                lock: true
            });
        });
    return customerCombo;
};

Business.goodsCombo = function ($_obj, opts) {
    if ($_obj.length == 0) {
        return;
    }
    ;
    var opts = $
        .extend(
            true,
            {
                data: '/Manage/GoodsGridData',
                ajaxOptions: {
                    formatData: function (data) {
                        parent.SYSTEM.goodsInfo = data.rows; // ����
                        return data.rows;
                    }
                },
                formatText: function (data) {
                    if (!data.Spec) {
                        return data.Name;
                    } else {
                        return data.Name + '_' + data.Spec;
                    }
                },
                value: 'GoodsId',
                defaultSelected: -1,
                editable: true,
                extraListHtml: '<a href="javascript:void(0);" id="quickAddGoods" class="quick-add-link"><i class="ui-icon-add"></i>������Ʒ</a>',
                maxListWidth: 500,
                cache: false,
                forceSelection: true,
                maxFilter: 10,
                trigger: false,
                listHeight: 182,
                listWrapCls: 'ui-droplist-wrap',
                loadOnce: false,
                callback: {
                    onChange: function (data) {
                        if (data) {
                            var parentTr = this.input.parents('tr');

                            parentTr.data('goodsInfo', {
                                GoodsId: data.GoodsId,
                                Number: data.Number,
                                Name: data.Name,
                                Spec: data.Spec,
                                Unit: data.Unit
                            });
                        }
                    },
                    onListClick: function () {

                    }
                },
                queryDelay: 0,
                inputCls: 'edit_subject',
                wrapCls: 'edit_subject_wrap',
                focusCls: '',
                disabledCls: '',
                activeCls: ''
            }, opts);

    var goodsCombo = $_obj.combo(opts).getCombo();

    // ������Ʒ
    $('#quickAddGoods').on('click', function (e) {
        e.preventDefault();
        $.dialog({
            title: '������Ʒ',
            content: 'url:/Manage/GoodsEdit',
            data: {
                oper: 'add',
                callback: function (data, oper, dialogWin) {
                    var goodsId = data.GoodsId;

                    parent.SYSTEM.goodsInfo.push(data);

                    dialogWin && dialogWin.api.close();

                    goodsCombo.loadData(parent.SYSTEM.goodsInfo, '-1', false);

                    setTimeout(function () {
                        goodsCombo.selectByValue(goodsId, true);
                        $_obj.focus();
                    }, 10);
                }
            },
            width: 640,
            height: 530,
            max: false,
            min: false,
            cache: false,
            lock: true
        });
    });
    return goodsCombo;
};

Business.billsEvent = function (obj, type, flag) {
    var _self = obj;
    // ������¼
    $('.grid-wrap').on(
        'click',
        '.ui-icon-plus',
        function (e) {
            var cellEdit = $("#grid").jqGrid("getGridParam")['cellEdit'];
            if (!cellEdit)
                return;

            var rowId = $(this).parent().data('id');
            var newId = $('#grid tbody tr').length;
            var datarow = {
                id: _self.newId
            };
            var su = $("#grid").jqGrid('addRowData', _self.newId, datarow,
                'after', rowId);// 'before'
            if (su) {
                $(this).parents('td').removeAttr('class');
                $(this).parents('tr').removeClass(
                    'selected-row ui-state-hover');
                $("#grid").jqGrid('resetSelection');
                _self.newId++;
            }
        });
    // ɾ����¼
    $('.grid-wrap').on('click', '.ui-icon-trash', function (e) {
        var cellEdit = $("#grid").jqGrid("getGridParam")['cellEdit'];
        if (!cellEdit)
            return;

        if ($('#grid tbody tr').length === 2) {
            parent.Public.tips({
                type: 2,
                content: '���ٱ���һ����¼��'
            });
            return false;
        }
        var rowId = $(this).parent().data('id');
        var su = $("#grid").jqGrid('delRowData', rowId);
        if (su) {
            _self.calTotal && _self.calTotal();
        }
        ;
    });
    // �������
    $('.grid-wrap').on('click', '.ui-icon-ellipsis', function (e) {

        $.dialog({
            width: 620,
            height: 500,
            title: 'ѡ����Ʒ',
            content: 'url:/Manage/GoodsBatch',
            data: {
                curId: _self.curId,
                newId: _self.newId,
                callback: function (newId, curId, curRow) {
                    if (curId === '') {
                        $("#grid").jqGrid('addRowData', newId, {}, 'last');
                        _self.newId = newId + 1;
                    }
                    ;
                    setTimeout(function () {
                        $("#grid").jqGrid("editCell", curRow, 2, true)
                    }, 10);
                    _self.calTotal && _self.calTotal();
                }
            },
            lock: true,
            ok: function () {
                this.content.callback(type);
                // return false;
            },
            cancel: true
        });
    });
    // ȡ����¼�༭״̬
    $(document).bind(
        'click.cancel',
        function (e) {
            if (!$(e.target).closest(".ui-jqgrid-bdiv").length > 0
                && curRow !== null && curCol !== null) {
                $("#grid").jqGrid("saveCell", curRow, curCol);
                curRow = null;
                curCol = null;
            }
            ;
        });

};


function reloadImageView() {
    var outerDivId = "outerDiv";
    var innerDivId = "innerDiv";
    var imgSrcPaths = null;
    var imgInfos = null;
    var width = 300;
    var height = 400;
    var autoScrooll = true;
    var time = 2000;
    var arrowControl = true;
    var speed = 500;
    var numberControl = false;

    var html = "";
    switch (arguments.length) {
        case 11:
            numberControl = arguments[10];
        case 10:
            speed = arguments[9];
        case 9:
            arrowControl = arguments[8];
        case 8:
            time = arguments[7];
        case 7:
            autoScrooll = arguments[6];
        case 6:
            height = arguments[5];
        case 5:
            width = arguments[4];
        case 4:
            imgInfos = arguments[3];
        case 3:
            imgSrcPaths = arguments[2];
        case 2:
            innerDivId = arguments[1];
        case 1:
            outerDivId = arguments[0];
            break;
        default:
            return;
    }

    html += "<div class='yiz-slider-3 yiz-slider' id='" + innerDivId + "' data-yiz-slider='3' style='width:" + width + "px;height:" + height + "px'><ul>";
    if (imgSrcPaths == null || imgSrcPaths == '' || imgSrcPaths.length == 0) {
        html += "<li><img src='../images/nothing.png' alt='There is nothing to View.' /></li>";
    } else {
        for (var index in imgSrcPaths) {
            var imgInfo = 'This picture has no description.';
            if (imgInfos != null && imgInfos.length > 0) {
                try {
                    imgInfo = imgInfos[index];
                } catch (e) {
                    imgInfo = '';
                }
            }
            html += "<li><a class='fancybox-buttons' data-fancybox-group='button' href='" + imgSrcPaths[index] + "' title='" + imgInfo + "'><img src='" + imgSrcPaths[index] + "' alt='" + imgInfo + "' /></a></li>";
        }
    }
    html += "</ul></div>";

    $('#' + outerDivId).html(html);

    $('#' + innerDivId).ScrollPic({
        Time: time,
        speed: speed,
        autoscrooll: autoScrooll,
        arrowcontrol: arrowControl,
        numbercontrol: numberControl
    });
}

// $.fn.toObject jQuery wrapper for form2object()
(function ($) {

    /**
     * jQuery wrapper for form2object() Extracts data from child inputs into
     * javascript object
     */
    $.fn.toObject = function (options) {
        var result = [], settings = {
            mode: 'first', // what to convert: 'all' or 'first' matched node
            delimiter: ".",
            skipEmpty: true,
            nodeCallback: null,
            useIdIfEmptyName: false
        };

        if (options) {
            $.extend(settings, options);
        }

        switch (settings.mode) {
            case 'first':
                return form2js(this.get(0), settings.delimiter, settings.skipEmpty,
                    settings.nodeCallback, settings.useIdIfEmptyName);
                break;
            case 'all':
                this.each(function () {
                    result.push(form2js(this, settings.delimiter,
                        settings.skipEmpty, settings.nodeCallback,
                        settings.useIdIfEmptyName));
                });
                return result;
                break;
            case 'combine':
                return form2js(Array.prototype.slice.call(this),
                    settings.delimiter, settings.skipEmpty,
                    settings.nodeCallback, settings.useIdIfEmptyName);
                break;
        }
    }

})(jQuery);

//�Ƿ�������
function isNumber(value) {
    var re = /^\d+(\.\d+)?$/;
    if (!re.test(value)) return false;
    return true;
}

//�ж��Ƿ�������
function isInt(value) {
    var re = /^[0-9]+[0-9]*]*$/;
    if (!re.test(value)) return false;
    return true;
}

//�ж��Ƿ��Ǹ�����
function isFloat(value) {
    var re = /^[-0-9]+([.]\d{1,2})?$/;
    ;
    if (!re.test(value)) return false;
    return true;
}

//�����ж�
function isEmail(email) {
    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (filter.test(email)) {
        return true;
    }
    return false;
}

//�ֻ��͵绰
function isTel(tel) {
    var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
    var isMob = /^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
    if (isMob.test(tel) || isPhone.test(tel)) {
        return true;
    } else {
        return false;
    }
}

/**
 * С��λ������
 * @param num
 * @param digits
 * @returns
 */
function numberDecimalDigits(num, digits) {
    return num.toFixed(digits);
}

/**
 * ���ڱȽϿ���
 * @param startDate
 * @param endDate
 * @returns
 */
function dateCompare(startDate, endDate) {
    if (startDate == '' || endDate == '') return true;
    var arrStart = startDate.split("-");
    var startTime = new Date(arrStart[0], arrStart[1], arrStart[2]);
    var startTimes = startTime.getTime();

    var arrEnd = endDate.split("-");
    var endTime = new Date(arrEnd[0], arrEnd[1], arrEnd[2]);
    var endTimes = endTime.getTime();

    if (startTimes > endTimes) {
        return false;
    } else
        return true;

}

//����jquery easyui loading cssЧ��   
function showLoading() {
    $("<div class=\"datagrid-mask\"></div>").css({
        display: "block",
        width: "100%",
        height: $(window).height()
    }).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html("���ڴ������Ժ򡣡���").appendTo("body").css({
        display: "block",
        left: ($(document.body).outerWidth(true) - 190) / 2,
        top: ($(window).height() - 45) / 2
    });
}

function hideLoading() {
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}

/**
 * �ж����֤�����Ƿ�Ϸ�
 * @param card
 * @returns {Boolean}
 */
function isCardNo(card) {
    //���֤����Ϊ15λ����18λ��15λʱȫΪ���֣�18λǰ17λΪ���֣����һλ��У��λ������Ϊ���ֻ��ַ�X  
    var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
    if (reg.test(card) === false) {
        return false;
    }

    return true;
}

/**
 * ȥ���ַ�����β�ո�
 * @param str
 * @returns
 */
function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * WolfSoul
 * ���֤���Ƿ�ϸ��У�鷽��,ǿ����У��,���ð�...
 * @param idNumber
 * @return
 */
function checkIdNumber(idNumber) {
    idNumber = trim(idNumber);
    var Errors = new Array("���֤��ʽ����", "���֤����������ڳ�����Χ���зǷ��ַ�!", "���֤�������,����������֤�Ų����Ӧ�κ���!", "���֤������ʾ�ĵ�������Ƿ�!");

    //У�鳤�ȣ����� 
    if (isCardNo(idNumber) === false) {
        return Errors[0];
    } else {
        return "";
    }
    /*   2015-09-09��������������֤����У��
    var area={11:"����",12:"���",13:"�ӱ�",14:"ɽ��",15:"���ɹ�",21:"����",22:"����",23:"������",31:"�Ϻ�",32:"����",33:"�㽭",34:"����",35:"����",36:"����",37:"ɽ��",41:"����",42:"����",43:"����",44:"�㶫",45:"����",46:"����",50:"����",51:"�Ĵ�",52:"����",53:"����",54:"����",61:"����",62:"����",63:"�ຣ",64:"����",65:"�½�",71:"̨��",81:"���",82:"����",91:"����"};  
    var Y,JYM; 
    var S,M; 
    var idNumber_array = new Array(); 
    idNumber_array = idNumber.split(""); 
    if(area[parseInt(idNumber.substr(0,2))]==null) 
        return Errors[3]; 

    switch(idNumber.length){ 
        case 15: 
            if( (parseInt(idNumber.substr(6,2))+1900) % 4 == 0 || ((parseInt(idNumber.substr(6,2))+1900) % 100 == 0 && (parseInt(idNumber.substr(6,2))+1900) % 4 == 0 )){ 
                ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//���Գ������ڵĺϷ��� 
            }else{ 
                ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//���Գ������ڵĺϷ��� 
            } 
            
            if(ereg.test(idNumber)){
                return "";
            }else{
                return Errors[1];
            }
            break; 
            
        case 18: 
            if ( parseInt(idNumber.substr(6,4)) % 4 == 0 || (parseInt(idNumber.substr(6,4)) % 100 == 0 && parseInt(idNumber.substr(6,4))%4 == 0 )){ 
                ereg=/^[1-9][0-9]{5}[1-9][0-9][0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//����������ڵĺϷ���������ʽ 
            }else{ 
                ereg=/^[1-9][0-9]{5}[1-9][0-9][0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//ƽ��������ڵĺϷ���������ʽ 
            } 
            if(ereg.test(idNumber)){
                S = (parseInt(idNumber_array[0]) 		 + parseInt(idNumber_array[10])) * 7 	
                        + (parseInt(idNumber_array[1]) + parseInt(idNumber_array[11])) * 9 
                        + (parseInt(idNumber_array[2]) + parseInt(idNumber_array[12])) * 10 	
                        + (parseInt(idNumber_array[3]) + parseInt(idNumber_array[13])) * 5
                        + (parseInt(idNumber_array[4]) + parseInt(idNumber_array[14])) * 8
                        + (parseInt(idNumber_array[5]) + parseInt(idNumber_array[15])) * 4
                        + (parseInt(idNumber_array[6]) + parseInt(idNumber_array[16])) * 2
                        +  parseInt(idNumber_array[7]) * 1
                        +  parseInt(idNumber_array[8]) * 6
                        +  parseInt(idNumber_array[9]) * 3 ; 
                
                Y = S % 11;
                M = "F";
                JYM = "10X98765432"; 
                M = JYM.substr(Y,1);
                if(M == idNumber_array[17]){
                    return "";
                }else{
                    return Errors[2];
                }
            }else{
                return Errors[1]; 
            }
            break; 
            
        default: 
            return "";
            break; 
    }
    */
}
  