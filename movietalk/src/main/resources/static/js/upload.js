const fileInput = document.querySelector("[name='file']");

const showUploadImages = (files) => {
  const output = document.querySelector(".uploadResult ul");

  let tags = "";

  files.forEach((file) => {
    tags += `<li data-name="${file.imgName}" data-path="${file.path}" data-uuid="${file.uuid}">`;
    tags += `<a href="${file.imageURL}">`;
    tags += `<img src="/upload/display?fileName=${file.thumbnailURL}" class="block>`;
    tags += "</a>";
    tags += `<span class="text-sm d-inline-block mx-1>${file.imgName}</span>`;
    tags += `<a href="${file.imageURL}"><i class="fa-solid fa-xmark"></i></a>`;
    tags += "</li>";
  });

  output.insertAdjacentHTML("beforeend", tags);
};

fileInput.addEventListener("change", (e) => {
  const files = fileInput.files;

  const formData = new FormData();
  for (let idx = 0; idx < files.length; idx++) {
    formData.append("uploadFiles", files[idx]);
  }
  fetch("/upload/upload", {
    method: "post",
    body: formData,
  })
    .then((res) => res.json())
    .then((data) => {
      console.log(data);
      showUploadImages(data);
    });
});

// 削除
document.querySelector(".uploadResult").addEventListener("click", (e) => {
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
