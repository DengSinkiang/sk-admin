<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">描述</label>
        <el-input v-model="query.description" clearable placeholder="描述" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">日志类型</label>
        <el-input v-model="query.logType" clearable placeholder="日志类型" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="ID">
            <el-input v-model="form.logId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="描述">
            <el-input v-model="form.description" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="日志类型">
            <el-select v-model="form.logType" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.user_status"
                :key="item.id"
                :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="method">
            <el-input v-model="form.method" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="params">
            <el-input v-model="form.params" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="requestIp">
            <el-input v-model="form.requestIp" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="time">
            <el-input v-model="form.time" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="username">
            <el-input v-model="form.username" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="address">
            <el-input v-model="form.address" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="browser">
            <el-input v-model="form.browser" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="exceptionDetail">
            <el-input v-model="form.exceptionDetail" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="createTime">
            <el-input v-model="form.createTime" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="logId" label="ID" />
        <el-table-column prop="description" label="描述">
          <template slot-scope="scope">
            {{ dict.label.user_status[scope.row.description] }}
          </template>
        </el-table-column>
        <el-table-column prop="logType" label="日志类型">
          <template slot-scope="scope">
            {{ dict.label.user_status[scope.row.logType] }}
          </template>
        </el-table-column>
        <el-table-column prop="method" label="method" />
        <el-table-column prop="params" label="params" />
        <el-table-column prop="requestIp" label="requestIp" />
        <el-table-column prop="time" label="time" />
        <el-table-column prop="username" label="username" />
        <el-table-column prop="address" label="address" />
        <el-table-column prop="browser" label="browser" />
        <el-table-column prop="exceptionDetail" label="exceptionDetail" />
        <el-table-column prop="createTime" label="createTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','sysLog:edit','sysLog:del']" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudSysLog from '@/api/sysLog'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'


const defaultForm = { logId: null, description: null, logType: null, method: null, params: null, requestIp: null, time: null, username: null, address: null, browser: null, exceptionDetail: null, createTime: null }
export default {
  name: 'SysLog',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  dicts: ['user_status', 'user_status'],
  cruds() {
    return CRUD({ title: 'TestApi', url: 'api/sysLog', sort: 'logId,desc', crudMethod: { ...crudSysLog }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'sysLog:add'],
        edit: ['admin', 'sysLog:edit'],
        del: ['admin', 'sysLog:del']
      },
      rules: {
      },
      queryTypeOptions: [
        { key: 'description', display_name: '描述' },
        { key: 'logType', display_name: '日志类型' }
      ]
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
