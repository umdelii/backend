// 削除
document.querySelector(".uploadResult i").addEventListener("click", (e) => {
  e.preventDefault();
  console.log("イベント対象 ", e.target);

  const aTag = e.target.closest("a");
  const li = e.target.closest("li");
  // href
  console.log("属性値 ", aTag.getAttribute("href"));
  const href = aTag.getAttribute("href");

  // controller
  const formData = new FormData();
  formData.append("fileName", href);
  fetch("/upload/remove", {
    method: "post",
    body: formData,
  })
    .then((res) => res.text())
    .then((data) => {
      console.log(data);
      // 画面上のイメージも消す
      li.remove();
    });
});
