<template>
  <div class="app-container">
    课程列表

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.name" placeholder="课程名称"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
          <el-option :value="Normal" label="已发布"/>
          <el-option :value="Draft" label="未发布"/>
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

      <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="160"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="课程名称" width="200" />

      <el-table-column label="课程状态" width="80">
        <template slot-scope="scope">
          {{ scope.row.status==='Normal'?'已发布':'未发布' }}
        </template>
      </el-table-column>

      <el-table-column prop="lessonNum" label="课时数" width="100"/>

      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

      <el-table-column prop="viewCount" label="浏览数量" width="100" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <!--超链接-->
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程基本信息</el-button>
          </router-link>

            <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程大纲信息</el-button>
          </router-link>

          <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteCourseById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

   <!-- 分页 :单向绑定-->
    <el-pagination
      :current-page="page"  
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />

  </div>
</template>

<script>
import course from '@/api/edu/course'

export default {
    //写核心代码
    data(){
      return {
        list:null,
        page:1,
        limit:5,
        total:0,
        courseQuery:{}
      }
    },
    created(){
      this.getList()
    },
    methods:{
      //删除操作
      deleteCourseById(id){
          this.$confirm('此操作将永久删除课程记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
           course.deleteCourseById(id)
            .then(response =>{
          //提示信息
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.getList()
        })
        })
      },
      //不分页实现
      getList(){
        course.getListCourse()
        .then(reponse =>{
          this.list = reponse.data.list
        })
      },
      //分页实现  ---TODO-------
      getListPage(page=1){
        this.page = page
        teacher.getTeacherListPage(this.page,this.limit,this.teacherQuery)
        .then(reponse =>{
          // console.log(response)
          this.list = reponse.data.rows
          this.total = reponse.data.total
          console.log(this.list) 
          console.log(this.total)
        })
        .catch(error =>{
          console.log(error)
        })
      },
      resetData(){
        this.courseQuery = {}
        this.getList()
      }
    }
}
</script>