"use strict";(self.__LOADABLE_LOADED_CHUNKS__=self.__LOADABLE_LOADED_CHUNKS__||[]).push([[168],{720687:(e,t,n)=>{n.d(t,{default:()=>r});var i=n(934980);let a=`pulsing {
  0% {
    opacity: 1;
  }

  50% {
    opacity: 0.4;
  }

  100% {
    opacity: 1;
  }
}`,r={css:(0,i.Ll)([a]),animation:"pulsing 2s infinite"}},934980:(e,t,n)=>{n.d(t,{CC:()=>a,Ll:()=>o,XF:()=>r});let i=(e,t,n)=>({x:Math.floor(e*Math.cos(n)),y:Math.floor(t*Math.sin(n))}),a=(e,t)=>i(t/2,e/2,2*Math.random()*Math.PI),r=(e,t)=>Math.floor(Math.random()*(t-e+1))+e,o=e=>["@-webkit-keyframes","@keyframes"].map(t=>e.map(e=>t+" "+e).join("\n")).join("\n")},31723:(e,t,n)=>{n.r(t),n.d(t,{default:()=>S});var i=n(667294),a=n(883119),r=n(573706),o=n(986782);function l(e,t,n){var i;return(t="symbol"==typeof(i=function(e,t){if("object"!=typeof e||!e)return e;var n=e[Symbol.toPrimitive];if(void 0!==n){var i=n.call(e,t||"default");if("object"!=typeof i)return i;throw TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(t,"string"))?i:i+"")in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}let s={},m=e=>{let t=e.__id||e.id;return"string"==typeof t&&t||null};class u{constructor(){l(this,"idMap",new Map),l(this,"objMap",new WeakMap)}get(e){let t=m(e);return this.objMap.get(e)??(t?this.idMap.get(t):void 0)}has(e){let t=m(e);return this.objMap.has(e)??(!!t&&this.idMap.has(t))}set(e,t){let n=m(e);n&&this.idMap.set(n,t),this.objMap.set(e,t)}reset(){this.idMap=new Map,this.objMap=new WeakMap}}function p(e,t){return"number"==typeof e?e:"_lg1"===t?e[t]??e.lg??1:e[t]??1}var d=n(587435),c=n(39260),h=n(876594),f=n(720687),y=n(512541),_=n(785893);let{css:g,animation:x}=f.default,b={backgroundColor:h._VP,animation:x,borderRadius:h.Ev2};function v({data:e}){let{height:t}=e;return(0,_.jsxs)(i.Fragment,{children:[(0,_.jsx)(y.Z,{unsafeCSS:g}),(0,_.jsx)(a.xu,{dangerouslySetInlineStyle:{__style:b},"data-test-id":"skeleton-pin",children:(0,_.jsx)(a.xu,{height:t})})]})}var w=n(679482),C=n(297728),M=n(730212),$=n(410150),j=n(415787),k=n(855746);function S(e){let{align:t,cacheKey:n,id:l,isFetching:m,isGridCentered:h=!0,items:f,layout:g,loadItems:x,masonryRef:b,optOutFluidGridExperiment:S=!1,renderItem:W,scrollContainerRef:E,virtualize:I=!0,_getColumnSpanConfig:R,_getResponsiveModuleConfigForSecondItem:A,_dynamicHeights:F,useLoadingState:N,isLoadingAccessibilityLabel:O,isLoadedAccessibilityLabel:P}=e,D=(0,$.ZP)(),{isAuthenticated:L,isRTL:Z}=(0,M.B)(),{logContextEvent:B}=(0,r.v)(),H=(0,C.F)(),G="desktop"===D,T=(0,k.MM)(),z=((0,i.useRef)(f.map(()=>({fetchTimestamp:Date.now(),measureTimestamp:Date.now(),hasRendered:!1,pageCount:0}))),G&&!S),{experimentalColumnWidth:V,experimentalGutter:X}=(0,d.Z)(z),K=e.serverRender??!!G,U="flexible"===g||"uniformRowFlexible"===g||"desktop"!==D||z,J=(U&&g?.startsWith("uniformRow")?"uniformRowFlexible":void 0)??(K?"serverRenderedFlexible":"flexible"),q=e.columnWidth??V??w.yF;U&&(q=Math.floor(q));let Q=e.gutterWidth??X??(G?w.oX:1),Y=e.minCols??w.yc,ee=((0,i.useRef)(0),q+Q),et=function(e){if(null==e)return;let t=function(e){let t=s[e];return t&&t.screenWidth===window.innerWidth||(s[e]={screenWidth:window.innerWidth}),s[e]}(e);return t.measurementCache||(t.measurementCache=new u),t.measurementCache}(n),en=(0,i.useCallback)(()=>E?.current||window,[E]),ei=(0,i.useRef)(!0),{anyEnabled:ea}=F?H.checkExperiment("dynamic_heights_v2"):{anyEnabled:!1},{anyEnabled:er}=H.checkExperiment("web_masonry_enable_dynamic_heights_for_all"),eo=h&&ei.current?"centered":"",{className:el,styles:es}=function(e){let t=`m_${Object.keys(e).sort().reduce((t,n)=>{let i=e[n];return null==i||"object"==typeof i||"function"==typeof i?t:"boolean"==typeof i?t+(i?"t":"f"):t+i},"").replace(/\:/g,"\\:")}`,{flexible:n,gutterWidth:i,isRTL:a,itemWidth:r,maxColumns:o,minColumns:l,items:s,_getColumnSpanConfig:m,_getResponsiveModuleConfigForSecondItem:u}=e,d=m?s.map((e,t)=>({index:t,columnSpanConfig:m(e)??1})).filter(e=>1!==e.columnSpanConfig):[],c=r+i,h=Array.from({length:o+1-l},(e,t)=>t+l).map(e=>{let h,f;let y=e===l?0:e*c,_=e===o?null:(e+1)*c-.01;m&&u&&s.length>1&&(h=m(s[0]),f=u(s[1]));let{styles:g,numberOfVisibleItems:x}=d.reduce((a,o)=>{let{columnSpanConfig:l}=o,m=Math.min(function({columnCount:e,columnSpanConfig:t,firstItemColumnSpanConfig:n,isFlexibleWidthItem:i,secondItemResponsiveModuleConfig:a}){let r=e<=2?"sm":e<=4?"md":e<=6?"_lg1":e<=8?"lg":"xl",o=p(t,r);if(i){let t=p(n,r);o="number"==typeof a?a:a?Math.max(a.min,Math.min(a.max,e-t)):1}return o}({columnCount:e,columnSpanConfig:l,isFlexibleWidthItem:!!f&&o===s[1],firstItemColumnSpanConfig:h??1,secondItemResponsiveModuleConfig:f??1}),e),u=null!=o.index&&a.numberOfVisibleItems>=m+o.index,d=n?100/e*m:r*m+i*(m-1),{numberOfVisibleItems:c}=a;return u?c-=m-1:o.index<c&&(c+=1),{styles:a.styles.concat(function({className:e,index:t,columnSpanConfig:n,visible:i,width:a,flexible:r}){let o="number"==typeof n?n:btoa(JSON.stringify(n));return r?`
      .${e} .static[data-column-span="${o}"]:nth-child(${t+1}) {
        visibility: ${i?"visible":"hidden"} !important;
        position: ${i?"inherit":"absolute"} !important;
        width: ${a}% !important;
      }`:`
      .${e} .static[data-column-span="${o}"]:nth-child(${t+1}) {
        visibility: ${i?"visible":"hidden"} !important;
        position: ${i?"inherit":"absolute"} !important;
        width: ${a}px !important;
      }`}({className:t,index:o.index,columnSpanConfig:l,visible:u,width:d,flexible:n})),numberOfVisibleItems:c}},{styles:"",numberOfVisibleItems:e}),b=n?`
      .${t} .static {
        box-sizing: border-box;
        width: calc(100% / ${e}) !important;
      }
    `:`
      .${t} {
        max-width: ${e*c}px;
      }

      .${t} .static {
        width: ${r}px !important;
      }
    `;return{minWidth:y,maxWidth:_,styles:`
      .${t} .static:nth-child(-n+${x}) {
        position: static !important;
        visibility: visible !important;
        float: ${a?"right":"left"};
        display: block;
      }

      .${t} .static {
        padding: 0 ${i/2}px;
      }

      ${b}

      ${g}
    `}}),f=h.map(({minWidth:e,maxWidth:t,styles:n})=>`
    @container (min-width: ${e}px) ${t?`and (max-width: ${t}px)`:""} {
      ${n}
    }
  `),y=h.map(({minWidth:e,maxWidth:t,styles:n})=>`
    @media (min-width: ${e}px) ${t?`and (max-width: ${t}px)`:""} {
      ${n}
    }
  `),_=`
    ${f.join("")}
    @supports not (container-type: inline-size) {
      ${y.join("")}
    }
  `;return{className:t,styles:`
  .masonryContainer {
      container-type: inline-size;
    }

    .masonryContainer > .centered {
      margin-left: auto;
      margin-right: auto;
    }

    .${t} .static {
      position: absolute !important;
      visibility: hidden !important;
    }

    ${_}
  `}}({gutterWidth:Q,flexible:U,items:f,isRTL:Z,itemWidth:q,maxColumns:e.maxColumns??Math.max(f.length,w.g5),minColumns:Y,_getColumnSpanConfig:R,_getResponsiveModuleConfigForSecondItem:A}),em=`${eo} ${el}`.trim(),{anyEnabled:eu,expName:ep,group:ed,isMeasureAllEnabled:ec}=(0,c.Z)(),eh=((0,i.useRef)(),(0,i.useRef)(f.length)),ef=(0,i.useRef)(0);(0,i.useEffect)(()=>{eh.current=f.length,ef.current+=1},[f]),(0,i.useEffect)(()=>{ei.current&&(ei.current=!1)},[]),(0,i.useEffect)(()=>()=>{},[]);let ey=(0,i.useCallback)(e=>{let t=e.reduce((e,t)=>e+t),n=t/e.length;(0,j.S0)("webapp.masonry.multiColumnWhitespace.average",n,{sampleRate:1,tags:{experimentalMasonryGroup:ed||"unknown",handlerId:T,isAuthenticated:L,multiColumnItemSpan:e.length}}),(0,j.S0)("webapp.masonry.twoColWhitespace",n,{sampleRate:1,tags:{columnWidth:q,minCols:Y}}),B({event_type:15878,component:14468,aux_data:{total_whitespace_px:t}}),B({event_type:16062,component:14468,aux_data:{average_whitespace_px:n}}),B({event_type:16063,component:14468,aux_data:{max_whitespace_px:Math.max(...e)}}),e.forEach(t=>{t>=50&&((0,j.nP)("webapp.masonry.multiColumnWhitespace.over50",{sampleRate:1,tags:{experimentalMasonryGroup:ed||"unknown",handlerId:T,isAuthenticated:L,multiColumnItemSpan:e.length}}),B({event_type:16261,component:14468})),t>=100&&((0,j.nP)("webapp.masonry.multiColumnWhitespace.over100",{sampleRate:1,tags:{experimentalMasonryGroup:ed||"unknown",handlerId:T,isAuthenticated:L,multiColumnItemSpan:e.length}}),B({event_type:16262,component:14468}))}),(0,j.nP)("webapp.masonry.multiColumnWhitespace.count",{sampleRate:1,tags:{experimentalMasonryGroup:ed||"unknown",handlerId:T,isAuthenticated:L,multiColumnItemSpan:e.length}})},[q,B,Y,L,T,ed]),{_items:e_,_renderItem:eg}=function({initialLoadingStatePinCount:e=50,infiniteScrollPinCount:t=10,isFetching:n,items:a=[],renderItem:r,useLoadingState:o}){let l=a.filter(e=>"object"==typeof e&&null!==e&&"type"in e&&"closeup_module"===e.type).length>0,s=o&&n&&0===a.length,m=o&&n&&l&&1===a.length,u=o&&n&&a.length>(l?1:0),p=(0,i.useMemo)(()=>Array.from({length:u?t:e}).reduce((e,t,n)=>[...e,{height:n%2==0?356:236,key:`skeleton-pin-${n}`,isSkeleton:!0}],[]),[e,t,u]);return{_items:(0,i.useMemo)(()=>m||u?[...a,...p]:s?p:a,[s,u,m,a,p]),_renderItem:(0,i.useMemo)(()=>o?e=>{let{itemIdx:t,data:n}=e;return t>=a.length&&n&&"object"==typeof n&&"key"in n&&"height"in n?(0,_.jsx)(v,{data:n},n.key):r(e)}:r,[o,r,a.length])}}({useLoadingState:N,items:f,renderItem:(0,i.useCallback)(e=>(0,_.jsx)(o.Z,{name:"MasonryItem",children:W(e)}),[W]),isFetching:m}),ex=N&&m;return(0,_.jsxs)(i.Fragment,{children:[N&&!ei.current&&(0,_.jsx)(a.xu,{"aria-live":"polite",display:"visuallyHidden",children:ex?O:P}),(0,_.jsx)("div",{"aria-busy":N?!!ex:void 0,className:"masonryContainer","data-test-id":"masonry-container",id:l,style:z?{padding:`0 ${Q/2}px`}:void 0,children:(0,_.jsxs)("div",{className:em,children:[K&&ei.current?(0,_.jsx)(y.Z,{"data-test-id":"masonry-ssr-styles",unsafeCSS:es}):null,(0,_.jsx)(a.xu,{"data-test-id":"max-width-container",marginBottom:0,marginEnd:"auto",marginStart:"auto",marginTop:0,maxWidth:e.maxColumns?ee*e.maxColumns:void 0,children:eu?(0,_.jsx)(a.GX,{ref:e=>{b&&(b.current=e)},_dynamicHeights:er||F,_dynamicHeightsV2Experiment:ea,_getColumnSpanConfig:R,_getResponsiveModuleConfigForSecondItem:A,_logTwoColWhitespace:ey,_measureAll:ec,align:t,columnWidth:q,gutterWidth:Q,items:e_,layout:U?J:g??"basic",loadItems:x,measurementStore:et,minCols:Y,renderItem:eg,scrollContainer:en,virtualBufferFactor:.3,virtualize:I}):(0,_.jsx)(a.Rk,{ref:e=>{b&&(b.current=e)},_dynamicHeights:er||F,_dynamicHeightsV2Experiment:ea,_getColumnSpanConfig:R,_getResponsiveModuleConfigForSecondItem:A,_logTwoColWhitespace:ey,align:t,columnWidth:q,gutterWidth:Q,items:e_,layout:U?J:g??"basic",loadItems:x,measurementStore:et,minCols:Y,renderItem:eg,scrollContainer:en,virtualBufferFactor:.3,virtualize:I})})]})})]})}},587435:(e,t,n)=>{n.d(t,{Z:()=>i});function i(e=!0){let t=e?16:void 0,n=t?Math.floor(t/4):void 0;return{experimentalColumnWidth:e?221:void 0,experimentalGutter:t,experimentalGutterBoints:n}}},39260:(e,t,n)=>{n.d(t,{Z:()=>o});var i=n(297728),a=n(730212),r=n(855746);function o(e){let{isAuthenticated:t}=(0,a.B)(),{expName:n,anyEnabled:o,group:l}=function({experimentsClient:e,handlerId:t,isAuthenticated:n,skipActivation:i}){let{checkExperiment:a}=e,r=a(n?"web_masonry_v2_auth":"web_masonry_v2_unauth",{dangerouslySkipActivation:i});return r.group?{expName:n?"web_masonry_v2_auth":"web_masonry_v2_unauth",...r}:"www/[username]/[slug].js"!==t||n?"www/pin/[id].js"!==t||n?{expName:"",anyEnabled:!1,group:""}:{expName:"web_masonry_v2_unauth_pin",...a("web_masonry_v2_unauth_pin",{dangerouslySkipActivation:i})}:{expName:"web_masonry_v2_unauth_board",...a("web_masonry_v2_unauth_board",{dangerouslySkipActivation:i})}}({experimentsClient:(0,i.F)(),handlerId:(0,r.MM)(),isAuthenticated:t,skipActivation:e?.skipActivation??!1});return{expName:n,anyEnabled:o,group:l,isMeasureAllEnabled:"enabled_measure_all"===l||"enabled_measure_all_impression_fix"===l,isImpressionFixEnabled:"control_impression_fix"===l||"enabled_impression_fix"===l||"enabled_measure_all_impression_fix"===l}}}}]);
//# sourceMappingURL=https://sm.pinimg.com/webapp/168-69a5b91d63e09957.mjs.map