<template>
  <div>
  <div id="cate_all">
    <!-- 카테고리 영역 -->
    <div id="category" class="xans-element- xans-layout xans-layout-category">
      <div class="position">
        <ul>
          <!-- 카테고리 리스트 -->
          <li class="xans-record-">
            <a href="/product/list.html?cate_no=111">NEW</a>
          </li>
          <li class="xans-record-">
            <a href="/product/list.html?cate_no=66">STREAMER</a>
          </li>
          <li class="xans-record-">
            <a href="/product/list.html?cate_no=101">컵</a>
          </li>
          <li class="xans-record-">
            <a href="/product/list.html?cate_no=107">디지털</a>
          </li>
          <li class="xans-record-">
            <a href="/product/list.html?cate_no=103">패션</a>
          </li>
          <li class="xans-record-">
            <a href="/product/list.html?cate_no=108">데스크</a>
          </li>
          <li class="xans-record-">
            <a href="/product/list.html?cate_no=140">인형</a>
          </li>
          <li class="xans-record-">
            <a href="/product/list.html?cate_no=149">키링</a>
          </li>
        </ul>
      </div>
    </div>
  </div>
  </div>
</template>

<script>
// Vue 컴포넌트에 필요한 스크립트 추가 (현재는 빈 스크립트)
</script>


<script></script>

<style scoped>
/* 전체 카테고리 영역 스타일 */
#cate_all {
    height: 33px;
    border-top: 1px solid #ececec;
    border-bottom: 1px solid #ececec;
    position: absolute;
    width: 100%;
    top: 140px;
    padding: 19px 0 28px;
}

/* 기본 폰트 및 배경 스타일 */
body, code {
    font: 0.75em 'Open Sans', 'Nanum Gothic', 'Malgun Gothic', '맑은 고딕', Dotum, '돋움', 'AppleGothic', 'Apple SD Gothic Neo', sans-serif;
    color: #111111;
    background: #fff;
}

/* 기본 요소 스타일 리셋 */
html, body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, code, form, fieldset, legend, input, textarea, p, blockquote, th, td, img {
    margin: 0;
    padding: 0;
    font-family: 'Open Sans', 'Nanum Gothic', 'Malgun Gothic', '맑은 고딕', Dotum, '돋움', 'AppleGothic', 'Apple SD Gothic Neo', sans-serif;
}

/* div 기본 스타일 */
div {
    display: block;
    unicode-bidi: isolate;
}

/* 카테고리 레이아웃 스타일 */
.xans-layout-category {
    position: absolute;
    min-width: 1200px;
    margin: 0 0 10px;
    width: 100%;
    text-align: center;
    clear: both;
    background: #fff;
    z-index: 1001;
}

/* 카테고리 리스트 스타일 */
.xans-layout-category .position {
    text-align: center;
}

.xans-layout-category .position > ul {
    /* 추가 스타일 없음 */
}

/* 카테고리 리스트 항목 스타일 */
.xans-layout-category .position > ul > li {
    position: relative;
    display: inline-block;
    z-index: 15;
    list-style: none;
}

#cate_all a, #header a {
    font-family: 'Raleway', 'Nanum Gothic';
}

/* 카테고리 링크 스타일 */
.xans-layout-category .position > ul > li > a {
    display: block;
    font-size: 13px;
    padding: 12px 14px 34px;
    letter-spacing: 1px;
    color: #000000;
    word-spacing: -1px;
    text-decoration: none;
    transition: all 0.3s ease;
}

/* 앵커 기본 스타일 */
a {
    text-decoration: none;
    color: #000;
    transition: all 0.3s ease;
}

</style>
