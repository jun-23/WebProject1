<template>
  <div class="total">
    <div class="post-detail-container">
      <div class="post-detail-content">
        <h3 class="post-detail-title">제목 : {{ store.post.title }}</h3>
        <h4 v-if="store.post.user" class="post-detail-author">
          쓴이 : {{ store.post.user.email }}
        </h4>
        <p class="post-detail-text">{{ store.post.content }}</p>
        <div v-if="store.post.user" class="edit-delete-buttons">
          <section class="btn-section">
            <button
              v-if="store.post.user.email === loginStore.JustUserInfo.email"
              @click="updatePost"
              class="btn-cp"
            >
              <i class="fab fa-lg first-logo fa-codepen"
                ><font-awesome-icon :icon="['fas', 'pen-to-square']"
              /></i>
              <i class="fab fa-lg second-logo fa-codepen"
                ><font-awesome-icon :icon="['fas', 'pen-to-square']"
              /></i>
            </button>
            <button
              v-if="store.post.user.email === loginStore.JustUserInfo.email"
              @click="deletePost"
              class="btn-cp"
            >
              <i class="fab fa-lg first-logo fa-codepen"
                ><font-awesome-icon :icon="['fas', 'trash-can']"
              /></i>
              <i class="fab fa-lg second-logo fa-codepen"
                ><font-awesome-icon :icon="['fas', 'trash-can']"
              /></i>
            </button>
          </section>
        </div>
      </div>

      <div class="comment-section">
        <hr class="comment-divider" />
        <h4 class="comment-heading">댓글 {{ store.commentList.length }}개</h4>
        <table class="comment-table">
          <tr>
            <th style="width: 15%"><!-- 글쓴이 / 등록일 --></th>
            <th><!--내용--></th>
            <th style="width: 10%"><!-- 수정 / 삭제 공간--></th>
          </tr>

          <!-- 댓글 내용 V-for문 시작-->
          <tr
            v-for="comment in store.commentList"
            :key="comment.comments_id"
            class="comment-row"
          >
            <!-- 유저 이름 + 글 쓴 날짜-->
            <td>
              <span style="font-size: 0.9em; font-weight: bold">{{
                comment.user.email
              }}</span
              ><br />
              <span style="font-size: 0.6em">{{ comment.regDate }}</span>
            </td>
            <!-- 댓글 내용 OR 수정시에는 수정 Form-->
            <td
              v-if="editCommentId === comment.comments_id"
              class="edit-comment-cell"
            >
              <input
                type="text"
                v-model="editCommentContent"
                class="edit-comment-input"
              />
              <section class="btn-section">
                <button
                  @click="saveComment(comment.comments_id)"
                  class="btn-cp"
                >
                  <i class="fab fa-lg first-logo fa-codepen"
                    ><font-awesome-icon :icon="['fas', 'floppy-disk']"
                  /></i>
                  <i class="fab fa-lg second-logo fa-codepen"
                    ><font-awesome-icon :icon="['fas', 'floppy-disk']"
                  /></i>
                </button>
                <button @click="cancelEdit" class="btn-cp">
                  <i class="fab fa-lg first-logo fa-codepen"
                    ><font-awesome-icon :icon="['fas', 'xmark']"
                  /></i>
                  <i class="fab fa-lg second-logo fa-codepen"
                    ><font-awesome-icon :icon="['fas', 'xmark']"
                  /></i>
                </button>
              </section>
            </td>
            <td style="font-size: 0.75em" v-else>{{ comment.content }}</td>
            <!-- 해당하는 글쓴이만 수정/삭제 권한 있다.-->
            <td>
              <div v-if="comment.user" class="edit-delete-buttons">
                <section class="btn-section">
                  <button
                    v-if="comment.user.email === loginStore.JustUserInfo.email"
                    @click="editComment(comment.comments_id, comment.content)"
                    class="btn-cp"
                  >
                    <i class="fab fa-lg first-logo fa-codepen"
                      ><font-awesome-icon :icon="['fas', 'pen-to-square']"
                    /></i>
                    <i class="fab fa-lg second-logo fa-codepen"
                      ><font-awesome-icon :icon="['fas', 'pen-to-square']"
                    /></i>
                  </button>
                  <button
                    v-if="comment.user.email === loginStore.JustUserInfo.email"
                    @click="deleteComment(comment.comments_id)"
                    class="btn-cp"
                  >
                    <i class="fab fa-lg first-logo fa-codepen"
                      ><font-awesome-icon :icon="['fas', 'trash-can']"
                    /></i>
                    <i class="fab fa-lg second-logo fa-codepen"
                      ><font-awesome-icon :icon="['fas', 'trash-can']"
                    /></i>
                  </button>
                </section>
              </div>
            </td>
          </tr>
        </table>

        <div class="comment-input-section">
          <h5 class="comment-input-heading">댓글 입력</h5>
          <input
            type="text"
            id="newComment"
            placeholder="내용"
            v-model="newComment.content"
            class="comment-input"
          />
          <section class="btn-section">
            <button @click="addComment" class="btn-cp">
              <i class="fab fa-lg first-logo fa-codepen"
                ><font-awesome-icon :icon="['fas', 'pen']"
              /></i>
              <i class="fab fa-lg second-logo fa-codepen"
                ><font-awesome-icon :icon="['fas', 'pen']"
              /></i>
            </button>
          </section>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
///// for FontAwesome
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { fas } from "@fortawesome/free-solid-svg-icons";

library.add(fas);
/////
import { useComuStore } from "@/stores/comu";
import { useLoginStore } from "@/stores/login";
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

const PresentId = ref(route.params.id);
const PresentWriter = ref();
const loginuser = ref();

const store = useComuStore();
const loginStore = useLoginStore();

// 새로운 댓글 REF
const newComment = ref({
  content: "",
});
//댓글 달기
const addComment = function () {
  const email = loginStore.JustUserInfo.email; // 적절한 이메일 값으로 변경
  store.createComment(email, PresentId.value, newComment.value);
  newComment.value.content = ""; // 입력 필드 초기화
};
// 수정할 댓글 REF
const editCommentId = ref(null);
const editCommentContent = ref("");
// 댓글 수정 시작
const editComment = function (commentId, content) {
  editCommentId.value = commentId;
  editCommentContent.value = content;
};
// 댓글 수정 저장
const saveComment = function (commentId) {
  const email = loginStore.JustUserInfo.email; // 적절한 이메일 값으로 변경
  store.updateComment(PresentId.value, commentId, email, {
    content: editCommentContent.value,
  });
  editCommentId.value = null;
  editCommentContent.value = "";
};
// 댓글 수정 취소
const cancelEdit = function () {
  editCommentId.value = null;
  editCommentContent.value = "";
};

// 댓글 삭제
const deleteComment = function (commentId) {
  const email = loginStore.JustUserInfo.email; // 적절한 이메일 값으로 변경
  store.deleteComment(PresentId.value, commentId, email);
};

// 게시글 수정
const updatePost = function () {
  // 게시글 수정 페이지로 이동하면서 현재 포스트의 ID를 전달합니다.
  router.push({ name: "updatePost", params: { id: route.params.id } });
};

// 게시글 삭제
const deletePost = function () {
  const email = loginStore.JustUserInfo.email;
  store.deletePost(email, route.params.id);
};

onMounted(async () => {
  try {
    await Promise.all([
      store.getPost(PresentId.value),
      loginStore.getJustUserInfo(),
    ]);
    store.getCommentListListByPostId(PresentId.value);
  } catch (error) {
    console.error("Error loading data:", error);
  }
});
</script>

<style>
/*전체 스타일 적용*/
.total {
  padding-left: 15%;
  padding-right: 15%;
  padding-top: 2%;
}

/* 게시글 내용 스타일 */
.post-detail-content {
  background-color: rgba(255, 255, 255, 0.8);
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.post-detail-title {
  font-size: 24px;
  margin-bottom: 10px;
}

.post-detail-author {
  font-size: 18px;
  margin-bottom: 10px;
}

.post-detail-text {
  font-size: 16px;
  margin-bottom: 25px;
}

.comment-section {
  margin-top: 20px;
}

.comment-divider {
  border: 0.5px solid #ccc;
  margin: 20px 0;
}

.comment-heading {
  font-size: 20px;
}

.comment-table {
  width: 100%;
  border-collapse: collapse;
}

.comment-table th,
.comment-table td {
  border: 0px solid #ddd;
  padding: 8px;
}

.comment-row:nth-child(even) {
  background-color: #f2f2f2;
}

.edit-comment-cell {
  display: flex;
  align-items: center;
}

.edit-comment-input {
  flex: 1;
  margin-right: 10px;
  padding: 5px;
}

.comment-input-section {
  margin-top: 20px;
  margin-bottom: 30px;
}

.comment-input-heading {
  font-size: 14px;
}

.comment-input {
  width: calc(100% - 100px);
  padding: 10px;
  margin-top: 10px;
}

/* ------------- */
/* Basic Setting */
/* ------------- */

*,
*::before,
*::after {
  box-sizing: inherit;
}

::selection {
  color: #fff;
  background-color: #f3feff;
}

/* Setting for Button*/
:root {
  /* Font Color */
  --light-theme: #f3feff;

  /* Font Family */
  --fonts-style-x: "neuzeit-grotesk", sans-serif;
  --fonts-style-y: "neuzeit-grotesk", sans-serif;

  /* Font Weight */
  --light: 300;
  --regular: 400;
  --bold: 700;
  --black: 900;
}

/* ------------- */
/* Content Style */
/* ------------- */

.btn-section {
  display: flex;
}

.btn-section button {
  position: relative;
  width: 26px;
  height: 26px;
  margin: 0 10px;
  border-radius: 2px;
  border: none;
  outline: none;
  overflow: hidden;
  cursor: pointer;
  transition: 400ms;

  animation: zoom 500ms ease;
}

.btn-section button i {
  position: absolute;
  font-size: 1em;
  transition: 400ms;
}

.btn-section .btn-cp {
  background-color: #ebfaf0bb;
  color: #1e4d94;
}

.btn-section .first-logo {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  opacity: 1;
}

.btn-section .second-logo {
  position: absolute;
  top: calc(50% + 30px);
  left: 50%;
  transform: translate(-50%, -50%);
  opacity: 0;
}

.btn-section button:hover .first-logo {
  position: absolute;
  top: -30px;
  opacity: 0;
}

.btn-section button:hover .second-logo {
  top: 50%;
  opacity: 1;
}

.btn-section button:active {
  background-color: #ffffff00;
}

@keyframes zoom {
}
</style>
