@/*
    选择查询条件标签的参数说明:

    name : 查询条件的名称
    id : 查询内容的input框id
@*/
<div class="input-group">
    <div class="input-group-btn">
        <button data-toggle="dropdown" class="btn btn-white dropdown-toggle" type="button">
            ${name}
        </button>
    </div>
    <div>

        <script type="text/javascript" src="/static/js/jquery.min.js"></script>
        <script type="text/javascript" src="/static/fa/fg.menu.js"></script>

        <link type="text/css" href="/static/fa/fg.menu.css" media="screen" rel="stylesheet" />
        <link type="text/css" href="/static/fa/theme/ui.all.css" media="screen" rel="stylesheet" />

        <!-- styles for this example page only -->
        <style type="text/css">
            body { font-size:62.5%; margin:0; padding:0; }
            #menuLog { font-size:1.4em; margin:20px; }
            .hidden { position:absolute; top:0; left:-9999px; width:1px; height:1px; overflow:hidden; }

            .fg-button { clear:left; margin:0 4px 40px 20px; padding: .4em 1em; text-decoration:none !important; cursor:pointer; position: relative; text-align: center; zoom: 1; }
            .fg-button .ui-icon { position: absolute; top: 50%; margin-top: -8px; left: 50%; margin-left: -8px; }
            a.fg-button { float:left;  }
            button.fg-button { width:auto; overflow:visible; } /* removes extra button width in IE */

            .fg-button-icon-left { padding-left: 2.1em; }
            .fg-button-icon-right { padding-right: 2.1em; }
            .fg-button-icon-left .ui-icon { right: auto; left: .2em; margin-left: 0; }
            .fg-button-icon-right .ui-icon { left: auto; right: .2em; margin-left: 0; }
            .fg-button-icon-solo { display:block; width:8px; text-indent: -9999px; }	 /* solo icon buttons must have block properties for the text-indent to work */

            .fg-button.ui-state-loading .ui-icon { background: url(/static/fa/spinner_bar.gif) no-repeat 0 0; }
        </style>

        <!-- style exceptions for IE 6 -->
        <!--[if IE 6]>
        <style type="text/css">
            .fg-menu-ipod .fg-menu li { width: 95%; }
            .fg-menu-ipod .ui-widget-content { border:0; }
        </style>
        <![endif]-->

        <script type="text/javascript">
            $(function(){
                // BUTTONS
                $('.fg-button').hover(
                    function(){ $(this).removeClass('ui-state-default').addClass('ui-state-focus'); },
                    function(){ $(this).removeClass('ui-state-focus').addClass('ui-state-default'); }
                );


                // or from an external source
                $.get('/dept/findDeptListsHtml?deptId=43', function(data){ // grab content from another page
                    $('#flyout').menu({ content: data, flyOut: true });
                });
            });
        </script>
        <!-- theme switcher button -->
        <!--<script type="text/javascript" src="http://ui.jquery.com/applications/themeroller/themeswitchertool/"></script>-->
        <!--<script type="text/javascript"> $(function(){ $('<div style="position: absolute; top: 20px; right: 300px;" />').appendTo('body').themeswitcher(); }); </script>-->



        <p id="menuLog">You chose: <span id="menuSelection"></span></p>



        <a tabindex="0" href="/dept/findDeptListsHtml?deptId=43" class="fg-button fg-button-icon-right ui-widget ui-state-default ui-corner-all" id="flyout"><span class="ui-icon ui-icon-triangle-1-s"></span>flyout menu</a>



    </div>
</div>