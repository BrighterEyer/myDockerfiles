/**
 * Created by 张乐平 on 8/2 0002.
 */

jQuery(function () {
    function getTree() {
        // Some logic to retrieve, or generate tree structure
        return [
            {
                text: "实物资产",
                state: {
                    checked: true,
                    disabled: false,
                    expanded: true,
                    selected: true
                },
                nodes: [
                    {
                        text: "集团内出租房产",
                        state: {
                            checked: true,
                            disabled: false,
                            expanded: true,
                            selected: true
                        }
                    },
                    {
                        text: "集团外出租房产"
                    },
                    {
                        text: "在建工程"
                    },
                    {
                        text: "抵债资产"
                    },
                    {
                        text: "......"
                    }
                ]
            },
            {
                text: "股权资产",
                nodes: [
                    {
                        text: "长期股权投资"
                    },
                    {
                        text: "可供出售金融资产-股票"
                    },
                    {
                        text: "可供出售金融资产-股权"
                    },
                    {
                        text: "......"
                    }
                ]
            },
            {
                text: "债权资产",
                nodes: [
                    {
                        text: "银行存款"
                    },
                    {
                        text: "应收款"
                    },
                    {
                        text: "贷款"
                    },
                    {
                        text: "......"
                    }
                ]
            }
        ];
    }

    $('#tree').treeview({data: getTree()});
});
