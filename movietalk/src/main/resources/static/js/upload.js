const fileInput = document.querySelector("[name='file']");

const showUploadImages = (files) => {
  const output = document.querySelector(".uploadResult ul");

  let tags = "";

  files.forEach((file) => {
    tags += `<li data-name="${file.imgName}" data-path="${file.path}" data-uuid="${file.uuid}">`;
    tags += `<a href="${file.imageURL}">`;
    tags += `<img src="/upload/display?fileName=${file.thumbnailURL}" class="block>`;
    tags += "</a>";
    tags += `<span class="text-sm d-inline-block mx-1">${file.imgName}</span>`;
    tags += `<a href="${file.imageURL}"><i class="fa-solid fa-xmark"></i></a>`;
    tags += "</li>";
  });

  output.insertAdjacentHTML("beforeend", tags);
};

fileInput.addEventListener("change", (e) => {
  // files : input fileの属性
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

// 登録(or削除)ボタンsumbit
// submit 中止
// uploadResult liの情報収集した後 form hidden タグにappend

document.querySelector("#create-form").addEventListener("submit", (e) => {
  e.preventDefault();

  const attachInfos = document.querySelectorAll(".uploadResult li");

  let result = "";

  attachInfos.forEach((obj, idx) => {
    result += `<input type="hidden" name="movieImages[${idx}].imgName" value="${obj.dataset.name}">`;
    result += `<input type="hidden" name="movieImages[${idx}].uuid" value="${obj.getAttribute(
      "data-uuid"
    )}">`;
    result += `<input type="hidden" name="movieImages[${idx}].path" value="${obj.dataset.path}">`;
  });

  e.target.insertAdjacentHTML("beforeend", result);

  console.log(e.target.innerHTML);
  e.target.submit();
});
