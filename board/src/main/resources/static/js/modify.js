document.querySelector(".btn-danger").addEventListener("click", (e) => {
  const form = document.querySelector("#action-form");
  form.action = "/board/remove";
  form.submit();
});
