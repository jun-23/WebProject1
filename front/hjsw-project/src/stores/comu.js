import { ref } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

const REST_API_COMU = `http://localhost:8080/api/posts`;

export const useComuStore = defineStore("comu", () => {
  //커뮤니티 글 목록
  const postList = ref([]);

  const getPostList = function () {
    axios
      .get(`${REST_API_COMU}`)
      .then((response) => {
        postList.value = response.data;
        // console.log(response.data);
        // console.log(postList.value);
      })
      .catch((error) => {
        console.error("오류: ", error);
      });
  };

  //글 한개를 가져온다.
  const post = ref({});

  const getPost = function (id) {
    axios.get(`${REST_API_COMU}/${id}`).then((response) => {
      post.value = response.data;
    });
  };

  //글 생성
  const createPost = function (email, post) {
    axios({
      url: REST_API_COMU,
      method: "POST",
      params: { email: email },
      data: post,
    })
      .then(() => {
        router.push({ name: "communityPostList" });
      })
      .catch((err) => {
        // console.log(email);
        console.log(post);
        console.log(err);
      });
  };

  // 글에 대한 Comment 리스트를 가져온다.
  const commentList = ref({});
  const getCommentListListByPostId = function (id) {
    axios
      .get(`${REST_API_COMU}/${id}/comments`)
      .then((response) => {
        commentList.value = response.data;
        console.log(response.data);
        console.log(commentList.value);
      })
      .catch((error) => {
        console.error("오류: ", error);
      });
  };

  // 댓글을 생성한다.
  const createComment = function (email, postId, comment) {
    axios({
      url: `${REST_API_COMU}/${postId}/comments`,
      method: "POST",
      params: { email: email },
      data: comment,
    })
      .then((response) => {
        commentList.value.push(response.data);
        console.log("댓글이 생성되었습니다.");
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 댓글을 수정한다.
  const updateComment = function (postId, commentId, email, commentDetails) {
    axios({
      url: `${REST_API_COMU}/${postId}/comments/${commentId}`,
      method: "PUT",
      params: { email: email },
      data: commentDetails,
    })
      .then((response) => {
        // commentList에서 해당 댓글을 업데이트합니다.
        const index = commentList.value.findIndex(
          (comment) => comment.comments_id === commentId
        );
        if (index !== -1) {
          commentList.value[index] = response.data;
        }
        console.log("댓글이 수정되었습니다.");
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 댓글을 삭제한다.
  const deleteComment = function (postId, commentId, email) {
    axios({
      url: `${REST_API_COMU}/${postId}/comments/${commentId}`,
      method: "DELETE",
      params: { email: email },
    })
      .then(() => {
        commentList.value = commentList.value.filter(
          (comment) => comment.comments_id !== commentId
        );
        console.log("댓글이 삭제되었습니다.");
      })
      .catch((err) => {
        console.log(err);
      });
  };

  //글 하나를 삭제한다.
  const deletePost = function (email, id) {
    axios({
      url: `${REST_API_COMU}/${id}`,
      method: "DELETE",
      params: { email: email },
    })
      .then((response) => {
        console.log("게시글이 삭제되었습니다.");
        window.alert("게시글이 삭제되었습니다.");
        router.push({ name: "communityPostList" });
      })
      .catch((err) => {
        console.log("삭제 메서드 수행 중 id?", id);
        console.log("이메일은? : " + email);
        console.log(err);
      });
  };

  //게시글을 업데이트한다.
  const changedPost = ref({});
  const updatePost = function (id, email, postDetails) {
    axios({
      url: `${REST_API_COMU}/${id}`,
      method: "PUT",
      params: { email: email },
      data: postDetails,
    })
      .then(() => {
        router.push({ name: "communityPostList" });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return {
    postList,
    getPostList,
    post,
    getPost,
    commentList,
    getCommentListListByPostId,
    createPost,
    deletePost,
    changedPost,
    updatePost,
    createComment,
    updateComment,
    deleteComment,
  };
}
,{
  persist:true
});